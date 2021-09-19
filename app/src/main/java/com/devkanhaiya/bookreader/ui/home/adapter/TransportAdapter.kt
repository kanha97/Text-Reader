package com.devkanhaiya.bookreader.ui.home.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.TransportItemLayoutBinding

class TransportAdapter(private val listener: ClickListener) :
    RecyclerView.Adapter<TransportAdapter.TransportViewHolder>() {

    private val transportList = ArrayList<Transport>()

    inner class TransportViewHolder(val binding: TransportItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(transport: Transport) = with(binding) {
            textViewBoarding.text = transport.title
            textViewTransportId.text = transport.description
            textViewDate.text = transport.destinationDate
        }

        override fun onClick(v: View?) {
            listener.addOnClick(transportList[adapterPosition])
        }

    }

    fun getSpans(text: Spannable, start: Int, end: Int): Spannable {
        text.setSpan(
            ForegroundColorSpan(Color.BLACK),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return text
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
        holder.bindViews(transportList[position])
        holder.binding.cardViewTransportItem.setOnClickListener(holder)
    }

    override fun getItemCount(): Int = transportList.size

    fun setTransportList(list: ArrayList<Transport>) {
        transportList.addAll(list)
    }

    interface ClickListener {
        fun addOnClick(transport: Transport)
    }
}