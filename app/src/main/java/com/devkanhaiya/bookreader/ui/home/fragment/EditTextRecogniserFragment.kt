package com.devkanhaiya.bookreader.ui.home.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeEditTextRecogniserFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import java.util.*

class EditTextRecogniserFragment : BaseFragment(), View.OnClickListener {
    var binding: HomeEditTextRecogniserFragmentBinding? = null

    var textToSpeech: TextToSpeech? = null
    var text: String? = null
    var currentLanguageNumber: Int? = null

    private lateinit var options: FirebaseTranslatorOptions

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
        text = imageText
        binding?.editTextTextRecogniser?.setText(imageText)
        if (!imageText.isNullOrEmpty()) {
            val title = imageText.split(" ")
            binding?.editTextTitle?.setText(title[0])
            binding?.editTextDescription?.setText(title[0])

        }
        binding?.buttonSubmit?.setOnClickListener(this)
        binding?.imageViewSpeak?.setOnClickListener(this)
        binding?.btnTranslate?.setOnClickListener(this)
        showTutorial()
    }

    private fun showTutorial() {

        if (!appPreferences.getBoolean(Const.LOG_IN_FIRST)) {

            MaterialTapTargetPrompt.Builder(requireActivity())
                .setTarget(binding?.editTextTitle)
                .setPrimaryText("Title")
                .setSecondaryText("Enter Title")
                .setPromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                        appPreferences.putBoolean(Const.LOG_IN_FIRST, true)
                        appPreferences.putBoolean(Const.LOG_IN_FIRST_STORIES, true)

                    }
                }.show()

        }
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
                hideKeyBoard()
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
                            date, it, currentLanguageNumber ?: 0
                        )
                    }?.let {
                        list.add(
                            it
                        )
                    }

                    val listing: ArrayList<Transport?>? =
                        appPreferences.getArrayList(Const.TRANSPORT_DATA)

                    listing?.forEach {
                        if (it != null) {
                            list.add(it)
                        }
                    }
                    appPreferences.saveArrayList(
                        list, Const.TRANSPORT_DATA
                    )

                    val resultIntent = Intent()
                    resultIntent.putExtra(Const.ADDED_NEW_VALUE, 1)
                    requireActivity().setResult(Activity.RESULT_OK, resultIntent)
                    navigator.finish()
                }


            }

            binding?.imageViewSpeak -> {
                if (textToSpeech != null) {

                    textToSpeech?.stop();
                    textToSpeech?.shutdown();
                }
                convertText()
            }
            binding?.btnTranslate -> {
                askLanguageToTranslate()
            }
        }

    }

    private fun askLanguageToTranslate() {
        currentLanguageNumber = arguments?.getInt(Const.LANGUAGE_DETECT)
        var currentLanguage = FirebaseTranslateLanguage.EN
        when (currentLanguageNumber) {
            0 -> {
                currentLanguage = FirebaseTranslateLanguage.EN
            }
            1 -> {
                currentLanguage = FirebaseTranslateLanguage.HI
            }
            2 -> {
                currentLanguage = FirebaseTranslateLanguage.MR
            }
        }


        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builderSingle.setIcon(R.mipmap.ic_launcher)
        builderSingle.setTitle(getString(R.string.select_translate_language))

        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice)
        arrayAdapter.add("English")
        arrayAdapter.add("Hindi")
        arrayAdapter.add("Marathi")

        builderSingle.setNegativeButton(getString(R.string.cancel),
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        builderSingle.setAdapter(arrayAdapter,
            DialogInterface.OnClickListener { dialog, which ->
                val strName = arrayAdapter.getItem(which)
                val builderInner: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                builderInner.setMessage(strName)
                builderInner.setTitle(getString(R.string.confirm_translate_language))
                if (which == 0) {
                    options = FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(currentLanguage)
                        .setTargetLanguage(FirebaseTranslateLanguage.EN)
                        .build()
                    currentLanguage = FirebaseTranslateLanguage.EN

                } else if (which == 1) {
                    options = FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(currentLanguage)
                        .setTargetLanguage(FirebaseTranslateLanguage.HI)
                        .build()
                    currentLanguage = FirebaseTranslateLanguage.HI

                } else {
                    options = FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(currentLanguage)
                        .setTargetLanguage(FirebaseTranslateLanguage.MR)
                        .build()
                    currentLanguage = FirebaseTranslateLanguage.MR

                }
                Log.d("TAG", "askLanguageToTranslate: $which")
                builderInner.setPositiveButton(getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, which ->
                        translateText()

                        dialog.dismiss()
                    })
                builderInner.show()
            })
        builderSingle.show()

    }

    fun translateText() {

        val englishHindiTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

        // lifecycle.addObserver(englishHindiTranslator)

        englishHindiTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {

                Log.d("TAG", "translateText: ")
                englishHindiTranslator.translate(text ?: "not available")
                    .addOnSuccessListener { translatedText ->

                        binding?.editTextTextRecogniser?.setText(translatedText)
                        Log.d("TAG", "translateText: $translatedText")
                        englishHindiTranslator.close()
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "inside translateText: $exception")
                        // Error.
                        // ...
                    }

            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "translateText: $exception")

                // Model couldn’t be downloaded or other internal error.
                // ...
            }

    }

    override fun onPause() {
        super.onPause()

        if (textToSpeech != null) {

            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
    }

    override fun onStop() {
        super.onStop()
        if (textToSpeech != null) {

            textToSpeech?.stop();
            textToSpeech?.shutdown();
        }
    }

    private fun convertText() {
        textToSpeech = TextToSpeech(requireContext(), TextToSpeech.OnInitListener() {
            if (it === TextToSpeech.SUCCESS) {
                val result: Int? = when (currentLanguageNumber) {
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
                Locale.getAvailableLocales().forEach {
                    Log.d("TAG", "convertText:$it ")

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
            textToSpeech?.speak(
                binding?.editTextTextRecogniser?.text?.trim()
                    .toString(), TextToSpeech.QUEUE_FLUSH, null
            )
        } else textToSpeech?.speak(
            binding?.editTextTextRecogniser?.text?.trim()
                .toString(), TextToSpeech.QUEUE_FLUSH, null
        )
    }
}