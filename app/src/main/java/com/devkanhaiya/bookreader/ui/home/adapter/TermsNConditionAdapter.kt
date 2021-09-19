package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.databinding.TermsNConditionsItemBinding

class TermsNConditionAdapter : RecyclerView.Adapter<TermsNConditionAdapter.TermsNConditionsViewHolder>() {

    private val termsNConditionsList = ArrayList<Pair<String, String>>()

    inner class TermsNConditionsViewHolder(private val binding: TermsNConditionsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(pair: Pair<String, String>) = with(binding) {
            textViewTNCTitle.text = pair.first
            textViewTNCDescription.text = pair.second
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsNConditionsViewHolder {
        return TermsNConditionsViewHolder(TermsNConditionsItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.terms_n_conditions_item, parent, false)))
    }

    override fun onBindViewHolder(holder: TermsNConditionsViewHolder, position: Int) {
        holder.bindViews(termsNConditionsList[position])
    }

    override fun getItemCount(): Int {
        return termsNConditionsList.size
    }
    fun setList(list:ArrayList<Pair<String, String>>){
        termsNConditionsList.addAll(list)
    }

}