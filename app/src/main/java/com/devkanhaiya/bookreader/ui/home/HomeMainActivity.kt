package com.devkanhaiya.bookreader.ui.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.databinding.DialogImageSelectionBinding
import com.devkanhaiya.bookreader.databinding.HomeMainActivityBinding
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.home.fragment.*
import com.devkanhaiya.bookreader.util.FilePickUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.home_header_layout.view.*
import kotlinx.android.synthetic.main.home_main_activity.*
import javax.inject.Inject

class HomeMainActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: HomeMainActivityBinding
    private var filePickUtils: FilePickUtils? = null


    private lateinit var mImageSelectionDialog: BottomSheetDialog
    lateinit var dialogImageSelectionBinding: DialogImageSelectionBinding
    var imagesPath = ""

    private val mOnFileChoose = object : FilePickUtils.OnFileChoose {
        override fun onFileChoose(fileUri: String, requestCode: Int) {
            imagesPath = fileUri
            // AppUtil.loadImages(context!!, fileUri, binding!!.imageViewProfilePicture)
        }
    }


    @Inject
    lateinit var appPreferences: AppPreferences

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