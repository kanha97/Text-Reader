package com.devkanhaiya.bookreader.ui.home.fragment

import android.graphics.drawable.ColorDrawable
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Document
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeOrderDetailsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.DocumentAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.ewayapp.util.AppUtil
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import java.util.*
import kotlin.collections.ArrayList


class OrderDetailsFragment : BaseFragment(), DocumentAdapter.Listener {

    var binding: HomeOrderDetailsFragmentBinding? = null
    var textToSpeech: TextToSpeech? = null
    var text: String? = null
    var transport: Transport? = null
    var list = ArrayList<Document>()

    private val adapter by lazy {
        DocumentAdapter(list, this)
    }

    override fun createLayout(): Int = R.layout.home_order_details_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeOrderDetailsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        val adLoader: AdLoader =
            AdLoader.Builder(requireContext(), requireContext().getString(R.string.native_id))
                .forNativeAd { nativeAd ->
                    val styles =
                        NativeTemplateStyle.Builder()
                            .withMainBackgroundColor(ColorDrawable(requireContext().getColor(R.color.gnt_white)))
                            .build()
                    binding?.myTemplate?.setStyles(styles)
                    binding?.myTemplate?.setNativeAd(nativeAd)
                }
                .build()

        adLoader.loadAd(AdManagerAdRequest.Builder().build())

        setUpToolBar()
        transport = arguments?.getParcelable<Transport>(Const.TRANSPORT)
        if (transport?.id == null) {
            binding?.textViewPhoto?.visibility = View.GONE
            transport?.directory?.let {
                AppUtil.loadImages(
                    requireContext(),
                    it, binding!!.imageViewPhoto
                )
            }
            text = transport?.text

        } else {
            binding!!.imageViewPhoto.visibility = View.INVISIBLE
            binding?.textViewPhoto?.text = transport?.text
            binding?.textViewPhoto?.movementMethod = ScrollingMovementMethod()
            if (transport?.language == 0) {
                val engWords =

                            "Lets Start..... "
                text = engWords + transport?.text

            } else if (transport?.language == 2) {
                val marathi =
                   "आपण प्रारंभ करूया"
                text = marathi + " " + transport?.text

            }else if (transport?.language == 1) {
                val marathi =
                    " शुरू करें ... \n"
                text = marathi + " " + transport?.text

            } else {
                text = transport?.text

            }
        }


        binding?.tvTitle?.text = transport?.title + ": " + transport?.description
        binding?.ivStop?.setOnClickListener {
            if (textToSpeech != null) {

                textToSpeech?.stop();
                textToSpeech?.shutdown();
            }
            navigator.goBack()
        }
        setUpTransportRecyclerView()
        addParts()
        if (list.isNotEmpty()) {
            adapter.listener.onPartClicked(list.get(0))
        }
    }

    var partsNo = 1
    private var docText = "Hello"
    private fun addParts() {
        docText = text.toString()
        addingPartsInto()
    }

    fun addingPartsInto() {
        if (!docText.isNullOrEmpty()) {
            if (docText.length < 2000) {
                list.add(Document(docText, "Part " + partsNo))
                partsNo += 1
            } else {
                list.add(Document(docText.substring(0, 2000), "Part " + partsNo))
                partsNo += 1
                docText = docText.substring(2000, docText.length)
                addingPartsInto()
            }
        }
    }

    private fun setUpTransportRecyclerView() {
        binding!!.recyclerViewStoryParts.apply {
            adapter = this@OrderDetailsFragment.adapter
        }
    }

    private fun setUpToolBar() {
        (activity as IsolatedActivity).showTitle(true, "Wait Starting.....")
        binding?.progressBar?.visibility=View.VISIBLE
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onPause() {
        if (textToSpeech != null) {

            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun convertText(docText: String) {
        if (textToSpeech != null) {

            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
        textToSpeech = TextToSpeech(requireContext(), TextToSpeech.OnInitListener() {
            if (it === TextToSpeech.SUCCESS) {
                val result: Int? = when (transport?.language) {
                    0 -> {
                        textToSpeech?.setLanguage(Locale("en", "IN"))
                    }
                    1 -> {
                        textToSpeech?.setLanguage(Locale("hi", "IN"))

                    }
                    2 -> {
                        textToSpeech?.setLanguage(Locale("mr_IN"))

                    }
                    else -> textToSpeech?.setLanguage(Locale("en", "IN"))


                }
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    Log.e("error", "This Language is not supported")
                } else {
                    ConvertTextToSpeech(docText)
                }
            } else Log.e("error", "Initilization Failed!")
        })
        if (textToSpeech != null) {
            textToSpeech!!.setPitch(1.10f)
            textToSpeech!!.setSpeechRate(1.10f)

        }
    }

    private fun ConvertTextToSpeech(docText: String) {
        textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                Log.d(TAG, "onStart: ")
            }

            override fun onDone(utteranceId: String?) {

                Log.d(TAG, "onDone: ")
            }

            override fun onError(utteranceId: String?) {

                Log.d(TAG, "onError: ")
            }
        })
        if (docText == null || "" == docText) {
            val emptytext = "Content not available"
            textToSpeech?.speak(emptytext, TextToSpeech.QUEUE_FLUSH, null)
        } else textToSpeech?.speak(docText.toString(), TextToSpeech.QUEUE_FLUSH, null)

        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_order_details))
        binding?.progressBar?.visibility=View.GONE

    }

    override fun onPartClicked(doc: Document) {
        convertText(doc.text)
        binding?.textViewPhoto?.text = doc.text
    }

    companion object {
        const val TAG = "DetailsScreen"
    }
}