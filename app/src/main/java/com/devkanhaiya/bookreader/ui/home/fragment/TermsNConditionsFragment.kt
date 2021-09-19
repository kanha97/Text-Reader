package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.HomeTermsNConditionsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.TermsNConditionAdapter

class TermsNConditionsFragment : BaseFragment() {

    var binding: HomeTermsNConditionsFragmentBinding? = null

    private val termsNConditionAdapter by lazy {
        TermsNConditionAdapter()
    }

    override fun createLayout(): Int = R.layout.home_terms_n_conditions_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeTermsNConditionsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        toolbar.showToolbar(true)
        toolbar.showBackButton(true)
        toolbar.setToolbarTitle(getString(R.string.title_terms_condition))
        val list = arrayListOf(
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est"),
                Pair("Lorem ipsum dolor sit amet.", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est")
        )
        termsNConditionAdapter.setList(list)
        binding!!.recyclerviewTermsNConditions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = termsNConditionAdapter
        }
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar.showBackButton(false)
    }
}