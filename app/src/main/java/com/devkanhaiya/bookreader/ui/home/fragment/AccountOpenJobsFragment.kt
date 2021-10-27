package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeAccountOpenJobsTableFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.TransportAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.google.firebase.database.*

class AccountOpenJobsFragment : BaseFragment(), TransportAdapter.ClickListener {

    var binding: HomeAccountOpenJobsTableFragmentBinding? = null
    var data: Transport? = null
    private val typesStoriesAdapter by lazy {
        TransportAdapter(this)
    }
    private lateinit var firebaseDatabaseReference: DatabaseReference

    override fun createLayout(): Int = R.layout.home_account_open_jobs_table_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeAccountOpenJobsTableFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference

        data = arguments?.getParcelable<Transport>(Const.TRANSPORT)
        setUpRecyclerView()

        data?.title?.let {
            (activity as IsolatedActivity).showTitle(
                true,
                it
            )
        }
    }

    private fun setUpRecyclerView() {
        val list: ArrayList<Transport?>? = arrayListOf(
        )
        firebaseDatabaseReference.child("storytypes")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    snapshot.children.forEach {
                        val transport: Transport? = it.getValue(Transport::class.java)
                        Log.d("TAG", "onDataChange: $transport")
                        transport?.isDeletable = false

                        if (data?.id == transport?.language?.toLong()) {
                            list?.add(transport)
                        }
                    }
                    typesStoriesAdapter.notifyDataSetChanged()
                    binding?.progressBar?.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: $error")

                }

            })
        binding!!.recyclerViewTypes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = typesStoriesAdapter
        }
        typesStoriesAdapter.setTransportList(list)
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun addOnClick(transport: Transport) {
        val bundle = Bundle()
        bundle.putParcelable(Const.TRANSPORT, transport)
        navigator.loadActivity(
            IsolatedActivity::class.java,
            NotificationFragment::class.java
        )
            .addBundle(bundle).start()

    }

    override fun addOnDeleteClick(transport: ArrayList<Transport?>) {

    }
}