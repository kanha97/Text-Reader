package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomeGetInTouchFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.exception.ApplicationException
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.devkanhaiya.bookreader.util.Validator
import javax.inject.Inject

class GetInTouchFragment : BaseFragment(), View.OnClickListener {

    var binding: HomeGetInTouchFragmentBinding? = null

    @Inject
    lateinit var validator: Validator
    override fun createLayout(): Int = R.layout.home_get_in_touch_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeGetInTouchFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
                (activity as IsolatedActivity).showTitle(true,"")

        binding!!.buttonSubmit.setOnClickListener(this)
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onClick(v: View?) {
        getInTouchValidation()
    }

    private fun getInTouchValidation() {
        try {
            validator.submit(binding!!.TextInputEditTextTitle).checkEmpty().errorMessage(getString(R.string.validation_title)).check()
            validator.submit(binding!!.TextInputEditTextEmail).checkEmpty().errorMessage(getString(R.string.validation_please_enter_email_address)).checkValidEmail().errorMessage(getString(R.string.validation_valid_email)).check()
            validator.submit(binding!!.TextInputEditTextWriteMsg).checkEmpty().errorMessage(getString(R.string.validation_message)).check()
            navigator.goBack()
        } catch (e: ApplicationException) {
            showError(e.message)
        }

    }
}