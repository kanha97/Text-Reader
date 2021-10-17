package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.SettingsItemCommonBinding
import com.devkanhaiya.bookreader.databinding.SettingsItemNotificationBinding
import com.devkanhaiya.bookreader.databinding.SettingsItemViewrightsBinding
import com.devkanhaiya.bookreader.ui.Const

class SettingsAdapter(private val listener: ClickListener, private val settingsList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var expandable = false

    private var notification: Boolean = false


    inner class SettingsCommonViewHolder(val binding: SettingsItemCommonBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(settings: String) = with(binding) {
            textViewSettingsTitle.text = settings
        }

        override fun onClick(v: View?) {
            listener.addOnClick(adapterPosition)
        }
    }

    inner class SettingsNotificationViewHolder(val binding: SettingsItemNotificationBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(settings: String) = with(binding) {
            textViewSettingsTitle.text = settings
        }

        override fun onClick(v: View?) {
            notification = binding.switchNotification.isChecked
            listener.addOnClick(adapterPosition)
        }

    }

    inner class SettingsViewRightsViewHolder(val binding: SettingsItemViewrightsBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(settings: String) = with(binding) {
            textViewSettingsTitle.text = settings
        }

        override fun onClick(v: View?) {
            expandable = !expandable
            notifyItemChanged(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.NOTIFICATION -> {
                SettingsNotificationViewHolder(SettingsItemNotificationBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.settings_item_notification, parent, false)))
            }
            Const.VIEW_RIGHT -> {
                SettingsViewRightsViewHolder(SettingsItemViewrightsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.settings_item_viewrights, parent, false)))
            }
            else -> {
                SettingsCommonViewHolder(SettingsItemCommonBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.settings_item_common, parent, false)))
            }
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Const.NOTIFICATION -> {
                (holder as SettingsNotificationViewHolder).bindViews(settingsList[position])
                holder.binding.switchNotification.setOnClickListener(holder)
                holder.binding.switchNotification.isChecked = notification

            }
            Const.VIEW_RIGHT -> {
                (holder as SettingsViewRightsViewHolder).bindViews(settingsList[position])
                holder.binding.textViewSettingsTitle.setOnClickListener(holder)
                holder.binding.layoutExpandable.visibility = if (expandable) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            Const.COMMON -> {
                (holder as SettingsCommonViewHolder).bindViews(settingsList[position])
                holder.binding.textViewSettingsTitle.setOnClickListener(holder)
            }
        }

    }

    override fun getItemCount(): Int {
        return settingsList.size
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            1 -> {
                Const.COMMON
            }
            2 -> {
                Const.COMMON
            }
            else -> {
                Const.COMMON
            }
        }
    }

    fun getNotification(): Boolean {
        return notification
    }

    fun setNotification(value: Boolean) {
        notification = value
    }

    interface ClickListener {
        fun addOnClick(position: Int)
    }
}