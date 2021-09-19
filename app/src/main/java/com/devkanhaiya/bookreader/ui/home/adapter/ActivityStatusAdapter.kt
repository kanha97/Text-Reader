package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.ActivityStatus
import com.devkanhaiya.bookreader.databinding.ActivityStatusItemBinding

class ActivityStatusAdapter(private val list: ArrayList<ActivityStatus>) : RecyclerView.Adapter<ActivityStatusAdapter.ActivityStatusViewHolder>() {

    inner class ActivityStatusViewHolder(private val binding: ActivityStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(activityStatus: ActivityStatus) = with(binding) {
            textViewActivityMilestone.text = activityStatus.activityMildStone
            textViewDateCompleted.text = activityStatus.dateCompleted
            textViewRemarkActivity.text = activityStatus.remarkActivity

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityStatusViewHolder {
        return ActivityStatusViewHolder(ActivityStatusItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.activity_status_item, parent, false)))
    }

    override fun onBindViewHolder(holder: ActivityStatusViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}