package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Notification
import com.devkanhaiya.bookreader.databinding.HomeNotificationFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.NotificationAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class NotificationFragment : BaseFragment() {

    var binding: HomeNotificationFragmentBinding? = null

    private val notificationAdapter by lazy {
        NotificationAdapter()
    }

    override fun createLayout(): Int = R.layout.home_notification_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeNotificationFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpToolBar()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val notificationList = arrayListOf(
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            ),
            Notification(
                "Lorem Ipsum is simply dummy text",
                "Lorem Ipsum is simply dummy text of the printing...",
                "25 Mar, 2020 - 3:00 pm"
            )
        )
        notificationAdapter.setList(notificationList)
        binding!!.recyclerViewNotification.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notificationAdapter
        }
    }

    override fun destroyViewBinding() {
        binding = null
    }


    private fun setUpToolBar() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_notification))
    }

}