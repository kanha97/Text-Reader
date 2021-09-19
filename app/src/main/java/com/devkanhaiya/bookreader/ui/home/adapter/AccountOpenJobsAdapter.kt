package com.devkanhaiya.bookreader.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.AccountOpenJobs
import com.devkanhaiya.bookreader.databinding.AccountOpenJobsTableItemBinding

class AccountOpenJobsAdapter : RecyclerView.Adapter<AccountOpenJobsAdapter.AccountOpenJobsViewHolder>() {

    private val accountOpenJobsList = ArrayList<AccountOpenJobs>()

    inner class AccountOpenJobsViewHolder(private val binding: AccountOpenJobsTableItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(accountOpenJobs: AccountOpenJobs) = with(binding) {
            textViewStatus.text = accountOpenJobs.status
            textViewShipper.text = accountOpenJobs.shipper
            textViewBuyer.text = accountOpenJobs.buyer
            textViewDate.text = accountOpenJobs.date

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountOpenJobsViewHolder {
        return AccountOpenJobsViewHolder(AccountOpenJobsTableItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.account_open_jobs_table_item, parent, false)))
    }

    override fun onBindViewHolder(holder: AccountOpenJobsViewHolder, position: Int) {
        holder.bindView(accountOpenJobsList[position])
    }

    override fun getItemCount(): Int {
        return accountOpenJobsList.size
    }

    fun setList(list: ArrayList<AccountOpenJobs>) {
        accountOpenJobsList.addAll(list)
    }
}