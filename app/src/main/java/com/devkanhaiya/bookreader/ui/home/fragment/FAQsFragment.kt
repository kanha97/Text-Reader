package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.FAQs
import com.devkanhaiya.bookreader.databinding.HomeFaqsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.FAQsAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class FAQsFragment : BaseFragment() {
    var binding: HomeFaqsFragmentBinding? = null

    private val faQsAdapter by lazy {
        FAQsAdapter(
            arrayListOf(
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                ),
                FAQs(
                    "Lorem ipsum dolor sit amet, consetetur sadipscing?",
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore"
                )
            )
        )
    }


    override fun createLayout(): Int = R.layout.home_faqs_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeFaqsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_faqs))
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding!!.recyclerViewFaqs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = faQsAdapter
        }

    }

    override fun destroyViewBinding() {
        binding = null
    }
}