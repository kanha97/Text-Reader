package com.devkanhaiya.bookreader.ui.home.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.*
import com.devkanhaiya.bookreader.databinding.HomeOrderDetailsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.home.adapter.ActivityStatusAdapter
import com.devkanhaiya.bookreader.ui.home.adapter.DeliverStatusAdapter
import com.devkanhaiya.bookreader.ui.home.adapter.DocumentAdapter
import com.devkanhaiya.bookreader.ui.home.adapter.TransportContainerDetailsAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class OrderDetailsFragment : BaseFragment() {

    var binding: HomeOrderDetailsFragmentBinding? = null
    private lateinit var transportContainerDetailsList: ArrayList<TransportContainerDetails>
    private lateinit var documentsList: ArrayList<Document>
    private lateinit var activityStatusList: ArrayList<ActivityStatus>
    private lateinit var deliveryStatusList: ArrayList<DeliveryStatus>


    private val deliveryStatusAdapter by lazy {
        DeliverStatusAdapter(deliveryStatusList)
    }


    private val transportContainerDetailsAdapter by lazy {
        TransportContainerDetailsAdapter(transportContainerDetailsList)
    }

    private val activityStatusAdapter by lazy {
        ActivityStatusAdapter(activityStatusList)
    }

    private val documentAdapter by lazy {

        DocumentAdapter(documentsList)
    }

    override fun createLayout(): Int = R.layout.home_order_details_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeOrderDetailsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpToolBar()

    }


    private fun setUpToolBar() {
        (activity as IsolatedActivity).showTitle(true,getString(R.string.title_order_details))

    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}