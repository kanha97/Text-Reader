package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.util.Log
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
import kotlin.collections.ArrayList

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
    var list: ArrayList<Transport?>? = null
    private fun setUpTransportRecyclerView() {

        list = appPreferences.getArrayList(Const.TRANSPORT_DATA)
        Log.d("TAG", "setUpTransportRecyclerView: ${list}")
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
        navigator.loadActivity(IsolatedActivity::class.java, OrderDetailsFragment::class.java)
            .addBundle(bundle).start()
    }

    override fun addOnDeleteClick(transport: ArrayList<Transport?>) {
        appPreferences.saveArrayList(transport,Const.TRANSPORT_DATA)
        transportAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeMainActivity).showFloatingActionButton(true)
    }

    override fun onStart() {
        val list = appPreferences.getArrayList(Const.TRANSPORT_DATA)

        transportAdapter.setTransportList(list)

        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showFloatingActionButton(false)

    }
}