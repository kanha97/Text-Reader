package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeNotificationFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.adapter.NotificationAdapter
import com.devkanhaiya.bookreader.ui.home.adapter.TransportAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.database.*

class NotificationFragment : BaseFragment(), TransportAdapter.ClickListener {

    var binding: HomeNotificationFragmentBinding? = null

    private lateinit var firebaseDatabaseReference: DatabaseReference

    var data: Transport? = null

    private val storiesAdapter by lazy {
        TransportAdapter(this)
    }


    private var mInterstitialAd: InterstitialAd? = null

    override fun createLayout(): Int = R.layout.home_notification_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeNotificationFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        firebaseDatabaseReference = FirebaseDatabase.getInstance().reference

        data = arguments?.getParcelable<Transport>(Const.TRANSPORT)

        setUpToolBar()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        val list: ArrayList<Transport?>? = arrayListOf(
        )
        firebaseDatabaseReference.child("stories")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    snapshot.children.forEach {
                        val transport: Transport? = it.getValue(Transport::class.java)
                        Log.d("TAG", "onDataChange: $transport")
                        transport?.isDeletable = false

                        if (data?.id == transport?.types) {
                            list?.add(transport)
                        }
                    }
                    storiesAdapter.notifyDataSetChanged()
                    binding?.progressBar?.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: $error")

                }

            })
        binding!!.recyclerViewStoriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = storiesAdapter
        }
        storiesAdapter.setTransportList(list)
    }

    override fun destroyViewBinding() {
        binding = null
    }


    private fun setUpToolBar() {
        (activity as IsolatedActivity).showTitle(true, getString(R.string.title_notification))
    }
    fun showAdd(transport: Transport) {
        mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when fullscreen content is dismissed.
                Log.d("TAG", "The ad was dismissed.")
                val bundle = Bundle()
                bundle.putParcelable(Const.TRANSPORT, transport)
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    OrderDetailsFragment::class.java
                )
                    .addBundle(bundle).start()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when fullscreen content failed to show.
                Log.d("TAG", "The ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when fullscreen content is shown.
                // Make sure to set your reference to null so you don't
                // show it a second time.
                mInterstitialAd = null
                Log.d("TAG", "The ad was shown.")
            }
        }

    }

    override fun addOnClick(transport: Transport) {

        val adRequest: AdRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),
            requireContext().getString(R.string.intertitial_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(@NonNull interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd
                    showAdd(transport)

                    mInterstitialAd?.show(requireActivity()) ?: Log.d(
                        "TAG",
                        "The interstitial ad wasn't ready yet."
                    )
                    Log.i("TAG", "onAdLoaded")
                }

                override fun onAdFailedToLoad(@NonNull loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.message)
                    val bundle = Bundle()
                    bundle.putParcelable(Const.TRANSPORT, transport)
                    navigator.loadActivity(
                        IsolatedActivity::class.java,
                        OrderDetailsFragment::class.java
                    )
                        .addBundle(bundle).start()
                    mInterstitialAd = null
                }
            })


    }

    override fun addOnDeleteClick(transport: ArrayList<Transport?>) {

    }

}