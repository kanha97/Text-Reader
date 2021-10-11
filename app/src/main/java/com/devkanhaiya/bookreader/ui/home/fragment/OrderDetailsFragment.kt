package com.devkanhaiya.bookreader.ui.home.fragment

import android.graphics.drawable.ColorDrawable
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeOrderDetailsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.ewayapp.util.AppUtil
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import java.util.*


class OrderDetailsFragment : BaseFragment() {

    var binding: HomeOrderDetailsFragmentBinding? = null
    var textToSpeech: TextToSpeech? = null
    var text: String? = null
    var transport: Transport? = null

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
        transport?.directory?.let {
            AppUtil.loadImages(
                requireContext(),
                it, binding!!.imageViewPhoto
            )
        }
        text = transport?.text
        convertText()

        binding?.tvTitle?.text = transport?.title
        binding?.ivStop?.setOnClickListener {
            if (textToSpeech != null) {

                textToSpeech?.stop();
                textToSpeech?.shutdown();
            }
            navigator.goBack()
        }

    }


    private fun setUpToolBar() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_order_details))

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

    private fun convertText() {
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
                    ConvertTextToSpeech()
                }
            } else Log.e("error", "Initilization Failed!")
        })
    }

    private fun ConvertTextToSpeech() {
        if (text == null || "" == text) {
            text = "Content not available"
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        } else textToSpeech?.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null)
    }
}