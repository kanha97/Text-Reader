package com.devkanhaiya.bookreader.ui.home.fragment

import android.app.Activity
import android.content.Intent
import android.os.SystemClock
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeEditTextRecogniserFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import java.util.*

class EditTextRecogniserFragment : BaseFragment(), View.OnClickListener {
    var binding: HomeEditTextRecogniserFragmentBinding? = null

    var textToSpeech: TextToSpeech? = null
    var text: String? = null

    override fun createLayout(): Int = R.layout.home_edit_text_recogniser_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeEditTextRecogniserFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_text_recognize))
        val imageText = arguments?.getString(Const.RECOGNIZED_TEXT)
        binding?.editTextTextRecogniser?.setText(imageText)
        binding?.buttonSubmit?.setOnClickListener(this)
        binding?.imageViewSpeak?.setOnClickListener(this)
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.buttonSubmit -> {
                Log.d("TAG", "onClick: ")
                val list = arrayListOf<Transport?>()
                val date: String = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                    .toString() + "/" + Calendar.getInstance().get(Calendar.MONTH)
                    .toString() + "/" + Calendar.getInstance().get(Calendar.YEAR)
                    .toString()

                if (binding?.editTextTextRecogniser?.text?.isBlank() == true) {
                    showError("Text Not Available...Please add text first ,Roshan")
                    Log.d("TAG", "onClick: Text Not Available...Please add text first ,Roshan")

                } else if (binding?.editTextTitle?.text?.isBlank() == true) {
                    showError("Please enter title... ,Roshan")
                    Log.d("TAG", "onClick: Please enter title... ,Roshan")

                } else if (binding?.editTextDescription?.text?.isBlank() == true) {
                    showError("Add Description... ,Roshan")
                    Log.d("TAG", "onClick: Add Description... ,Roshan")

                } else {
                    arguments?.getString(Const.DIRECTORY_DATA)?.let {
                        Transport(
                            binding?.editTextTextRecogniser?.text?.trim()
                                .toString(),
                            binding?.editTextTitle?.text?.trim().toString(),
                            binding?.editTextDescription?.text?.trim().toString(),
                            date, it
                        )
                    }?.let {
                        list.add(
                            it
                        )
                    }
                    Log.d("TAG", "onClick: $date")
                }
                val listing: ArrayList<Transport?>? = appPreferences.getArrayList(Const.TRANSPORT_DATA)
                listing?.forEach {
                    if (it != null) {
                        list.add(it)
                    }
                }
                appPreferences.saveArrayList(
                    list, Const.TRANSPORT_DATA
                )
                SystemClock.sleep(1000)
                val resultIntent = Intent()

                resultIntent.putExtra(Const.ADDED_NEW_VALUE, 1)
                requireActivity().setResult(Activity.RESULT_OK, resultIntent)
                navigator.finish()            }

            binding?.imageViewSpeak -> {
                convertText()
            }
        }

    }

    override fun onPause() {
        if (textToSpeech != null) {

            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
        super.onPause()
    }

    private fun convertText() {
        textToSpeech = TextToSpeech(requireContext(), TextToSpeech.OnInitListener() {
            if (it === TextToSpeech.SUCCESS) {
                val result: Int? = textToSpeech?.setLanguage(Locale.ENGLISH)
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
        text = binding?.editTextTextRecogniser?.getText().toString()
        if (text == null || "" == text) {
            text = "Content not available"
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        } else textToSpeech?.speak(text.toString(), TextToSpeech.QUEUE_FLUSH, null)
    }
}