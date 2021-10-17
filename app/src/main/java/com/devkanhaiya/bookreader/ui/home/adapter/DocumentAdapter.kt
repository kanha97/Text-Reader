package com.devkanhaiya.bookreader.ui.home.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Document
import com.devkanhaiya.bookreader.databinding.DocumentsItemsBinding

class DocumentAdapter(private val list: ArrayList<Document>, val listener: Listener) :
    RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {
    inner class DocumentViewHolder(val binding: DocumentsItemsBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bindViews(document: Document) = with(binding) {
            textViewDocument.text = document.title
            if (document.isWatched) {
                cardViewDocument.backgroundTintList =
                    ColorStateList.valueOf(cardViewDocument.context.getColor(R.color.colorPrimaryDark))
            }

        }

        override fun onClick(v: View?) {
            listener.onPartClicked(list.get(adapterPosition))
            binding.cardViewDocument.backgroundTintList =
                ColorStateList.valueOf(binding.cardViewDocument.context.getColor(R.color.colorPrimaryDark))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        return DocumentViewHolder(
            DocumentsItemsBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.documents_items, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bindViews(list[position])
        holder.binding.cardViewDocument.setOnClickListener(holder)

        if (position == 0) {
            holder.binding.cardViewDocument.backgroundTintList =
                ColorStateList.valueOf(holder.binding.cardViewDocument.context.getColor(R.color.colorPrimaryDark))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface Listener {
        fun onPartClicked(get: Document)
    }
}