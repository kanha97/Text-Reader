package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.TransportItemLayoutBinding
import com.ewayapp.util.AppUtil

class TransportAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<TransportAdapter.TransportViewHolder>() {

    private var transportList = ArrayList<Transport?>()

    inner class TransportViewHolder(val binding: TransportItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(transport: Transport) = with(binding) {
            textViewBoarding.text = transport.title
            textViewTransportId.text = transport.description
            textViewDate.text = transport.destinationDate
            AppUtil.loadImages(textViewBoarding.context, transport.directory, imageViewBack)
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.ivDelete -> {
                    transportList.remove(transportList[adapterPosition])
                    notifyDataSetChanged()
                    listener.addOnDeleteClick(transportList)

                }
                binding.cardViewTransportItem -> {
                    transportList[adapterPosition]?.let { listener.addOnClick(it) }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        return TransportViewHolder(
            TransportItemLayoutBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.transport_item_layout, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        transportList[position]?.let { holder.bindViews(it) }
        holder.binding.cardViewTransportItem.setOnClickListener(holder)
        holder.binding.ivDelete.setOnClickListener(holder)
    }

    override fun getItemCount(): Int = transportList.size

    fun setTransportList(list: ArrayList<Transport?>?) {
        if (list != null) {
            transportList = list
        }
    }

    fun addTransport(list: Transport?) {
        transportList.add(list)
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun addOnClick(transport: Transport)
        fun addOnDeleteClick(transport: ArrayList<Transport?>)
    }
}