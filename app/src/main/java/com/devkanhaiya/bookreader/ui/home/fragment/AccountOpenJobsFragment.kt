package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.AccountOpenJobs
import com.devkanhaiya.bookreader.databinding.HomeAccountOpenJobsTableFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.AccountOpenJobsAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity

class AccountOpenJobsFragment : BaseFragment() {

    var binding: HomeAccountOpenJobsTableFragmentBinding? = null

    private val accountOpenJobsAdapter by lazy {
        AccountOpenJobsAdapter()
    }

    override fun createLayout(): Int = R.layout.home_account_open_jobs_table_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeAccountOpenJobsTableFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpRecyclerView()
        (activity as IsolatedActivity).showTitle(
            true,
            getString(R.string.title_list_of_acc_open_jobs)
        )
    }

    private fun setUpRecyclerView() {
        val cancel = getString(R.string.dummy_cancel)
        val initiated = getString(R.string.dummy_initilized)
        val list = arrayListOf(
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "The Ben Sliver Corporation", "B & C Industries", "09/28/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "12/25/19"),
            AccountOpenJobs(initiated, "Arkonik Ltd.", "Brian C. Emery", "12/28/19"),
            AccountOpenJobs(cancel, "Inner Mongolia Lantai Sod..", "Interstate Che..", "09/25/19"),
            AccountOpenJobs(cancel, "Koyo Corporation of USA", "B & C Industries", "09/28/19")
        )

        binding!!.recyclerViewOpenJobs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountOpenJobsAdapter
        }
        accountOpenJobsAdapter.setList(list)
    }

    override fun destroyViewBinding() {
        binding = null
    }
}