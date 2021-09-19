package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomeChangePasswordFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.exception.ApplicationException
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.devkanhaiya.bookreader.util.Validator
import javax.inject.Inject

class ChangePasswordFragment : BaseFragment(), View.OnClickListener {

    var binding: HomeChangePasswordFragmentBinding? = null

    @Inject
    lateinit var validator: Validator

    override fun createLayout(): Int = R.layout.home_change_password_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeChangePasswordFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpToolbar()
        binding!!.buttonSubmit.setOnClickListener(this)
    }

    private fun setUpToolbar() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.label_change_password))
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onClick(v: View?) {
        changePasswordValidation()
    }

    private fun changePasswordValidation() {
        try {
            validator.submit(binding!!.TextInputEditTextCurrentPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_current_password)).check()
            validator.submit(binding!!.TextInputEditTextNewPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_new_password)).check()
            validator.submit(binding!!.TextInputEditTextConfirmPassword).checkEmpty()
                .errorMessage(getString(R.string.validation_confirm_password)).check()
            navigator.goBack()
        } catch (e: ApplicationException) {
            showError(e.message)
        }
    }
}