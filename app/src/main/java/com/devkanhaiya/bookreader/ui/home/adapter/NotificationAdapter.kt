package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Notification
import com.devkanhaiya.bookreader.databinding.NotificationItemBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    private val notificationList = ArrayList<Notification>()

    inner class NotificationViewHolder(private val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(notification: Notification) = with(binding) {
            textViewNotificationTitle.text = notification.notificationTitle
            textViewNotificationDescription.text = notification.notificationDescription
            textViewDate.text = notification.notificationDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {

        return NotificationViewHolder(NotificationItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bindViews(notificationList[position])

    }

    fun setList(list: ArrayList<Notification>) {
        notificationList.addAll(list)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}