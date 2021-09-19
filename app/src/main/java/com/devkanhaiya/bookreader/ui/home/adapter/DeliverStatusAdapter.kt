package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.DeliveryStatus
import com.devkanhaiya.bookreader.databinding.DeliveryStatusItemBinding

class DeliverStatusAdapter(private val list: ArrayList<DeliveryStatus>) : RecyclerView.Adapter<DeliverStatusAdapter.DeliveryStatusViewHolder>() {
    inner class DeliveryStatusViewHolder(private val binding: DeliveryStatusItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(deliveryStatus: DeliveryStatus) = with(binding) {
            textViewPOD.text = deliveryStatus.status
            textViewPODCity.text = deliveryStatus.city
            textViewPODDate.text = deliveryStatus.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryStatusViewHolder {

        return DeliveryStatusViewHolder(DeliveryStatusItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.delivery_status_item, parent, false)))
    }

    override fun onBindViewHolder(holder: DeliveryStatusViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}