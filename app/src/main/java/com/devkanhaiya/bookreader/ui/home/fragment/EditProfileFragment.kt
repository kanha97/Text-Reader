package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomeEditprofileFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.exception.ApplicationException
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.util.Validator
import javax.inject.Inject

class EditProfileFragment : BaseFragment(), View.OnClickListener {

    var binding: HomeEditprofileFragmentBinding? = null

    @Inject
    lateinit var validator: Validator

    override fun createLayout(): Int = R.layout.home_editprofile_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeEditprofileFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        toolbar.showToolbar(false)
        binding!!.imageViewBack.setOnClickListener(this)
        binding!!.buttonSave.setOnClickListener(this)

    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageViewBack -> {
                navigator.goBack()
            }
            R.id.buttonSave -> {
                editProfileValidation()
            }
        }

    }

    private fun editProfileValidation() {
        try {
            validator.submit(binding!!.TextInputEditTextName).checkEmpty().errorMessage(getString(R.string.validation_name)).check()
            validator.submit(binding!!.TextInputEditTextCompanyName).checkEmpty().errorMessage(getString(R.string.validation_company_name)).check()
            validator.submit(binding!!.TextInputEditTextMobileNo).checkEmpty().errorMessage(getString(R.string.validation_mobile_number)).checkMinDigits(10).errorMessage(getString(R.string.validation_min_ten_digit_mobile_number)).check()
            validator.submit(binding!!.TextInputEditTextEmail).checkEmpty().errorMessage(getString(R.string.validation_please_enter_email_address)).checkValidEmail().errorMessage(getString(R.string.validation_valid_email)).check()
            navigator.goBack()

        } catch (e: ApplicationException) {
            showError(e.message)
        }
    }
}