package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Document
import com.devkanhaiya.bookreader.databinding.DocumentsItemsBinding

class DocumentAdapter(private val list: ArrayList<Document>) : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {
    inner class DocumentViewHolder(private val binding: DocumentsItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(document: Document) = with(binding) {
            textViewDocument.text = document.title
            imageViewDocument.setImageResource(document.image)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        return DocumentViewHolder(DocumentsItemsBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.documents_items, parent, false)))
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}