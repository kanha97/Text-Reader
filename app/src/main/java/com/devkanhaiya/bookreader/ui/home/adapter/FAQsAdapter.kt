package com.devkanhaiya.bookreader.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.FAQs
import com.devkanhaiya.bookreader.databinding.FaqsItemBinding

class FAQsAdapter(private val list: ArrayList<FAQs>) : RecyclerView.Adapter<FAQsAdapter.FAQsViewHolder>() {

    inner class FAQsViewHolder(val binding: FaqsItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(faQs: FAQs) = with(binding) {
            textViewFAQTitle.text = faQs.title
            textViewFAQDescription.text = faQs.description
        }

        override fun onClick(v: View?) {
            list[adapterPosition].expandable = !list[adapterPosition].expandable
            notifyItemChanged(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQsViewHolder {
        return FAQsViewHolder(FaqsItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.faqs_item, parent, false)))
    }

    override fun onBindViewHolder(holder: FAQsViewHolder, position: Int) {
        holder.bindViews(list[position])

        holder.binding.textViewFAQTitle.setOnClickListener(holder)



        if (list[position].expandable) {
            holder.binding.textViewFAQDescription.visibility = View.VISIBLE
            holder.binding.textViewFAQTitle.setTextColor(Color.BLACK)

        } else {
            holder.binding.textViewFAQDescription.visibility = View.GONE
            holder.binding.textViewFAQTitle.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}