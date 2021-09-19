package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomeAboutUsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class AboutUsFragment : BaseFragment() {
    var binding: HomeAboutUsFragmentBinding? = null

    override fun createLayout(): Int = R.layout.home_about_us_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeAboutUsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
                (activity as IsolatedActivity).showTitle(true,getString(R.string.title_about_us))

    }

    override fun destroyViewBinding() {
        binding = null
    }
}