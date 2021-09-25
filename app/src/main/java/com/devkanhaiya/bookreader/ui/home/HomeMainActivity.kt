package com.devkanhaiya.bookreader.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.DialogImageSelectionBinding
import com.devkanhaiya.bookreader.databinding.HomeMainActivityBinding
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.home.fragment.EditTextRecogniserFragment
import com.devkanhaiya.bookreader.ui.home.fragment.HomeFragment
import com.devkanhaiya.bookreader.ui.home.fragment.SettingsFragment
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.devkanhaiya.bookreader.util.FilePickUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.home_main_activity.*


class HomeMainActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: HomeMainActivityBinding
    private var filePickUtils: FilePickUtils? = null
    var recognizer: TextRecognizer=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private lateinit var mImageSelectionDialog: BottomSheetDialog
    lateinit var dialogImageSelectionBinding: DialogImageSelectionBinding
    var imagesPath = ""

    private val mOnFileChoose = object : FilePickUtils.OnFileChoose {
        override fun onFileChoose(fileUri: String, requestCode: Int) {
            imagesPath = fileUri

//            val textRecognizer = TextRecognizer.Builder(applicationContext).build()
            //val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
           // val recognizer: TextRecognizer = TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())


        askLanguageDetection()


            /*val imageFrame = Frame.Builder()
                .setBitmap(BitmapFactory.decodeFile(imagesPath)) // your image bitmap
                .build()
            */
            //   binding.imageView.setImageBitmap(BitmapFactory.decodeFile(imagesPath))

            /* val textBlocks = textRecognizer.detect(imageFrame)

             for (i in 0 until textBlocks.size()) {
                 val textBlock = textBlocks[textBlocks.keyAt(i)]
                 imageText = imageText + " " + textBlock.value // return string
             }*/
        }
    }

    private fun askLanguageDetection() {

        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this)
        builderSingle.setIcon(R.mipmap.ic_launcher)
        builderSingle.setTitle("Select language to detect from image : ")

        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
        arrayAdapter.add("English")
        arrayAdapter.add("Hindi")
        arrayAdapter.add("Marathi")

        builderSingle.setNegativeButton("cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        builderSingle.setAdapter(arrayAdapter,
            DialogInterface.OnClickListener { dialog, which ->
                val strName = arrayAdapter.getItem(which)
                val builderInner: AlertDialog.Builder = AlertDialog.Builder(this)
                builderInner.setMessage(strName)
                builderInner.setTitle("Your Selected Language  is")
                if (which==0){
                     recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                }else{
                    recognizer = TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())
                }
                builderInner.setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, which ->
                        recognizeText()
                        dialog.dismiss() })
                builderInner.show()
            })
        builderSingle.show()

    }

    fun recognizeText(){
    var imageText = ""
    val image = InputImage.fromBitmap(BitmapFactory.decodeFile(imagesPath), 0)

    val result = recognizer
        .process(image)
        .addOnSuccessListener { visionText ->
            val resultText = visionText.text
            for (block in visionText.textBlocks) {
                val blockText = block.text
                val blockCornerPoints = block.cornerPoints
                val blockFrame = block.boundingBox

                for (line in block.lines) {
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    for (element in line.elements) {
                        val elementText = element.text
                        val elementCornerPoints = element.cornerPoints
                        val elementFrame = element.boundingBox
                        Log.d("TAG", "onFileChoose:element ${element.text} ")
                        Log.d("TAG", "onFileChoose:line ${line.text} ")
                        Log.d("TAG", "onFileChoose:block ${block.text} ")
                    }
                }
            }
            imageText=resultText
            Log.d("TAG", "onFileChoose:block ${imageText} ")

            loadActivity(
                IsolatedActivity::class.java,
                EditTextRecogniserFragment::class.java
            ).addBundle(Bundle().apply {
                putString(Const.RECOGNIZED_TEXT, imageText)
                putString(Const.DIRECTORY_DATA, imagesPath)
            }).forResult(10001).start()
        }
        .addOnFailureListener { e ->
            // Task failed with an exception
            // ...
        }

}

    override fun findFragmentPlaceHolder(): Int = R.id.placeHolder

    override fun findContentView(): Int = R.layout.home_main_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeMainActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.WHITE
        load(HomeFragment::class.java).add(false)
        setUpToolbar()
        showBackSymbol(false)
        setUpNavigationView()



        filePickUtils = FilePickUtils(this, mOnFileChoose)
        mImageSelectionDialog = BottomSheetDialog(this)

        binding.imageButtonBack.setOnClickListener(this)
        binding.floatingActionCamera.setOnClickListener(this)
        binding.floatingActionButtonWhatsApp.setOnClickListener(this)
    }


    private fun setUpNavigationView() {

        binding.imageButtonNavMenu.setOnClickListener {
        }

        navigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuHome -> {
                    load(HomeFragment::class.java).replace(false)
                }
                R.id.menuReport -> {
                    load(SettingsFragment::class.java).replace(true)
                }

            }
            true
        }
    }

    private fun setUpToolbar() {
        setToolbar(binding.toolbar)
    }


    fun showBackSymbol(b: Boolean) {
        binding.imageButtonBack.visibility = if (b) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    fun showFloatingActionButton(b: Boolean) {
        binding.floatingActionButtonWhatsApp.visibility = if (b) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun setToolbarTitle(title: CharSequence) {
        binding.textViewToolbar.text = title
        super.setToolbarTitle("")
    }

    override fun onClick(v: View?) {
        when (v) {

            binding.imageButtonBack -> {
                goBack()
            }
            binding.floatingActionCamera -> {
                showImageSelectionDialog()
            }
            binding.floatingActionButtonWhatsApp -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://chat.whatsapp.com/DX1l1OkEy9jGzBh52OyydI")
                )
                startActivity(intent)
            }


            dialogImageSelectionBinding.tvSelectCamera -> {
                filePickUtils?.requestImageCamera(
                    Const.PICK_IMAGE_CAMERA_REQUEST_CODE,
                    false,
                    false
                )
                mImageSelectionDialog.cancel()
            }

            dialogImageSelectionBinding.tvSelectGallery -> {
                filePickUtils?.requestImageGallery(
                    Const.PICK_IMAGE_GALLERY_REQUEST_CODE,
                    false,
                    false
                )
                mImageSelectionDialog.cancel()
            }
        }

    }


    ///edit profile
    private fun showImageSelectionDialog() {

        dialogImageSelectionBinding = DialogImageSelectionBinding.inflate(layoutInflater)
        mImageSelectionDialog.setContentView(dialogImageSelectionBinding.root)

        dialogImageSelectionBinding.tvSelectCamera.setOnClickListener(this)
        dialogImageSelectionBinding.tvSelectGallery.setOnClickListener(this)
        mImageSelectionDialog.show()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (intent != null) {
            if (intent.getIntExtra(Const.ADDED_NEW_VALUE, 0) == 1) {
                load(HomeFragment::class.java).clearHistory(HomeFragment::class.java.simpleName)
                    .replace(false)
            }
        }
        filePickUtils?.onActivityResult(requestCode, resultCode, intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePickUtils?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}