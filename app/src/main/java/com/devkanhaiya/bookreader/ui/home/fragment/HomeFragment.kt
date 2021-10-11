package com.devkanhaiya.bookreader.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.data.pojo.Transport
import com.devkanhaiya.bookreader.databinding.HomeHomeFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.home.adapter.TransportAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class HomeFragment : BaseFragment(), TransportAdapter.ClickListener {

    private val transportAdapter by lazy {
        TransportAdapter(this)
    }
    private var mInterstitialAd: InterstitialAd? = null

    private var binding: HomeHomeFragmentBinding? = null

    override fun createLayout(): Int = R.layout.home_home_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeHomeFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpToolBar()
        setUpTransportRecyclerView()

    }

    var list: ArrayList<Transport?>? = null
    private fun setUpTransportRecyclerView() {

        list = appPreferences.getArrayList(Const.TRANSPORT_DATA)
        Log.d("TAG", "setUpTransportRecyclerView: ${list}")
        transportAdapter.setTransportList(list)
        binding!!.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transportAdapter
        }
    }

    private fun setUpToolBar() {
        toolbar.showToolbar(true)
        (activity as HomeMainActivity).showBackSymbol(false)
        toolbar.setToolbarTitle(getString(R.string.title_home))
    }

    override fun destroyViewBinding() {
        binding = null
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

    override fun addOnDeleteClick(transport: ArrayList<Transport?>) {
        appPreferences.saveArrayList(transport, Const.TRANSPORT_DATA)
        transportAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeMainActivity).showFloatingActionButton(true)
    }

    override fun onStart() {
        val list = appPreferences.getArrayList(Const.TRANSPORT_DATA)

        transportAdapter.setTransportList(list)

        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showFloatingActionButton(false)

    }
}