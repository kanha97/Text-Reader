package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomePrivacyNPolicyFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment

class PrivacyNPolicyFragment : BaseFragment() {

    var binding: HomePrivacyNPolicyFragmentBinding? = null

    override fun createLayout(): Int = R.layout.home_privacy_n_policy_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomePrivacyNPolicyFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        toolbar.showToolbar(true)
        toolbar.showBackButton(true)
        toolbar.setToolbarTitle(getString(R.string.title_privacy_policy))

    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.showBackButton(false)
    }
}