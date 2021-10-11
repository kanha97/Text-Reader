package com.devkanhaiya.bookreader.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.home_main_activity.*


class HomeMainActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: HomeMainActivityBinding
    private var filePickUtils: FilePickUtils? = null
    var recognizer: TextRecognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private lateinit var mImageSelectionDialog: BottomSheetDialog
    lateinit var dialogImageSelectionBinding: DialogImageSelectionBinding
    var imagesPath = ""
    var language = 0

    private val mOnFileChoose = object : FilePickUtils.OnFileChoose {
        override fun onFileChoose(fileUri: String, requestCode: Int) {
            imagesPath = fileUri

        }
    }

    private fun askLanguageDetection() {

        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(this)
        builderSingle.setIcon(R.mipmap.ic_launcher)
        builderSingle.setTitle(getString(R.string.detect_image))

        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
        arrayAdapter.add(getString(R.string.english))
        arrayAdapter.add(getString(R.string.hindi))
        arrayAdapter.add(getString(R.string.marathi))

        builderSingle.setNegativeButton(getString(R.string.cancel),
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })

        builderSingle.setAdapter(arrayAdapter,
            DialogInterface.OnClickListener { dialog, which ->
                val strName = arrayAdapter.getItem(which)
                val builderInner: AlertDialog.Builder = AlertDialog.Builder(this)
                builderInner.setMessage(strName)
                builderInner.setTitle(getString(R.string.selected_language))
                if (which == 0) {
                    recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                } else {
                    recognizer =
                        TextRecognition.getClient(DevanagariTextRecognizerOptions.Builder().build())
                }
                language = which
                builderInner.setPositiveButton(getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, which ->
                        recognizeText()
                        dialog.dismiss()
                    })
                builderInner.show()
            })
        builderSingle.show()

    }

    fun recognizeText() {
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
                imageText = resultText
                Log.d("TAG", "onFileChoose:block ${imageText} ")

                loadActivity(
                    IsolatedActivity::class.java,
                    EditTextRecogniserFragment::class.java
                ).addBundle(Bundle().apply {
                    putString(Const.RECOGNIZED_TEXT, imageText)
                    putString(Const.DIRECTORY_DATA, imagesPath)
                    putInt(Const.LANGUAGE_DETECT, language)
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

        MobileAds.initialize(this) {
            Log.d("TAG", "onCreate: $it")
        }

        val adRequest = AdRequest.Builder().build();
        binding.adView.loadAd(adRequest)

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
                //    showImageSelectionDialog()
                ImagePicker.with(this)
                    .compress(1024)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
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
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri? = intent?.data

            if (uri != null) {
                imagesPath = uri?.path.toString()

                askLanguageDetection()

            }

            // Use Uri object instead of File to avoid storage permissions
            Log.d("TAG", "onActivityResult: $imagesPath")
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(intent), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
        if (intent != null) {
            if (intent.getIntExtra(Const.ADDED_NEW_VALUE, 0) == 1) {
                load(HomeFragment::class.java).clearHistory(HomeFragment::class.java.simpleName)
                    .replace(false)
            }
        }
        filePickUtils?.onActivityResult(requestCode, resultCode, intent)

        when (requestCode) {
            REQUEST_UPDATE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.IMMEDIATE) {
                        Toast.makeText(this, R.string.app_updated, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, R.string.app_started, Toast.LENGTH_SHORT).show()
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, R.string.app_cancelled, Toast.LENGTH_SHORT).show()
                } else if (resultCode == ActivityResult.RESULT_IN_APP_UPDATE_FAILED) {
                    Toast.makeText(this, R.string.app_updated_failed, Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> false
        }
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

    override fun onResume() {
        super.onResume()
        checkForUpdates()
    }

    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(this)


    fun checkForUpdates() {
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        try {
            appUpdateInfo.addOnSuccessListener {
                handleUpdate(appUpdateManager, appUpdateInfo)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun handleUpdate(
        appUpdateManager: AppUpdateManager,
        appUpdateInfo: Task<AppUpdateInfo>
    ) {
        if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.IMMEDIATE) {
            handleImmediateUpdate(appUpdateManager, appUpdateInfo)
        } else if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.FLEXIBLE) {
        //    handleFlexibleUpdate(appUpdateManager, appUpdateInfo)
        }
    }

    private fun handleImmediateUpdate(
        manager: AppUpdateManager,
        info: Task<AppUpdateInfo>
    ) {
        if ((info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                    info.result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
            && info.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

            try {
                manager.startUpdateFlowForResult(info.result,
                    AppUpdateType.IMMEDIATE, this, REQUEST_UPDATE)
            } catch (ignored: IntentSender.SendIntentException) {

            }
        } else if (info.result.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE) {
            showErrorMessage("Update not available")
        } else {
            showErrorMessage("Problem to connecting to play store")
        }

    }


    companion object {
        const val APP_UPDATE_TYPE_SUPPORTED = 1
        const val REQUEST_UPDATE = 1010

    }
}