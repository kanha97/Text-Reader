package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.TransportContainerDetails
import com.devkanhaiya.bookreader.databinding.TransportDetailsItemBinding

class TransportContainerDetailsAdapter(private val list: ArrayList<TransportContainerDetails>) : RecyclerView.Adapter<TransportContainerDetailsAdapter.TransportDetailsViewHolder>() {

    inner class TransportDetailsViewHolder(private val binding: TransportDetailsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(transportContainerDetails: TransportContainerDetails) = with(binding) {
            textViewContainerEdit.text = transportContainerDetails.container
            textViewTypeEdit.text = transportContainerDetails.type
            textViewWeightEdit.text = transportContainerDetails.weight
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportDetailsViewHolder {
        return TransportDetailsViewHolder(TransportDetailsItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.transport_details_item, parent, false)))
    }

    override fun onBindViewHolder(holder: TransportDetailsViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}