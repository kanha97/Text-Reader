package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeHomeFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.home.adapter.TransportAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class HomeFragment : BaseFragment(), TransportAdapter.ClickListener {

    private val transportAdapter by lazy {
        TransportAdapter(this)
    }

    private var binding: HomeHomeFragmentBinding? = null

    override fun createLayout(): Int = R.layout.home_home_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeHomeFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpToolBar()
        setUpTransportRecyclerView()
    }

    private fun setUpTransportRecyclerView() {
        val planeIcon = R.drawable.ic_plane
        val busIcon = R.drawable.ic_bus_front
        val shipIcon = R.drawable.ic_boat_with_containers
        val trainIcon = R.drawable.ic_train
        val list = arrayListOf(
            Transport("123", "Book Name", "Page 2", "25/01/2021", ""),
            Transport("123", "Book Name", "Page 2", "25/01/2021", ""),
            Transport("123", "Book Name", "Page 2", "25/01/2021", ""),
            Transport("123", "Book Name", "Page 2", "25/01/2021", "")
        )

        transportAdapter.setTransportList(list)
        binding!!.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transportAdapter
        }
    }

    private fun setUpToolBar() {
        toolbar.showToolbar(true)
        (activity as HomeMainActivity).showBackSymbol(false)
        toolbar.setToolbarTitle(getString(R.string.title_home))
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun addOnClick(transport: Transport) {
        val bundle = Bundle()
        bundle.putParcelable(Const.TRANSPORT, transport)
        navigator.loadActivity(IsolatedActivity::class.java,OrderDetailsFragment::class.java).addBundle(bundle).start()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeMainActivity).showFloatingActionButton(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showFloatingActionButton(false)

    }
}