package com.devkanhaiya.bookreader.ui.home.fragment

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.databinding.HomeSettingsFragmentBinding
import com.devkanhaiya.bookreader.di.component.FragmentComponent
import com.devkanhaiya.bookreader.ui.Const
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.home.adapter.SettingsAdapter
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import javax.inject.Inject


class SettingsFragment : BaseFragment(), SettingsAdapter.ClickListener {
    var binding: HomeSettingsFragmentBinding? = null

    @Inject
    lateinit var appPreference: AppPreferences

    lateinit var list: ArrayList<String>

    private val settingsAdapter by lazy {
        SettingsAdapter(this, list)
    }

    override fun createLayout(): Int = R.layout.home_settings_fragment

    override fun bindViewWithViewBinding(view: View) {
        binding = HomeSettingsFragmentBinding.bind(view)
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun bindData() {
        setUpRecyclerView()
        settingsAdapter.setNotification(appPreference.getBoolean(Const.SETTINGS_NOTIFICATION))
        toolbar.showToolbar(true)
        toolbar.setToolbarTitle(getString(R.string.title_settings))
    }

    private fun setUpRecyclerView() {
        list = arrayListOf(
            getString(R.string.title_about_us),
            getString(R.string.title_contact_us),
            getString(R.string.title_privacy_policy),
            getString(R.string.title_rate_app),
            getString(R.string.title_share_app)
        )
        binding!!.recyclerViewSettings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = settingsAdapter
        }
    }

    override fun destroyViewBinding() {
        binding = null
    }

    override fun addOnClick(position: Int) {
        when (position) {
            0 -> {
                navigator.loadActivity(IsolatedActivity::class.java, AboutUsFragment::class.java)
                    .start()
            }

            1 -> {
              /*  navigator.loadActivity(IsolatedActivity::class.java, GetInTouchFragment::class.java)
                    .start()*/
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://chat.whatsapp.com/DX1l1OkEy9jGzBh52OyydI")
                )
                startActivity(intent)

            }
            2 -> {
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    PrivacyNPolicyFragment::class.java
                ).start()
            }
            3 -> {
                rateApp()
            }
            4 -> {
                shareApp()
            }

        }
    }

    fun rateApp() {
        val url = "https://play.google.com/store/apps/details?id=com.devkanhaiya.bookreader"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
    fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Booker Reader")
            var shareMessage = "\nLet me recommend you this Cool application , It will help you read Images Text\n\n"
            shareMessage =
                """
                ${shareMessage}https://play.google.com/store/apps/details?id=com.devkanhaiya.bookreader}
                
                
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showBackSymbol(false)
    }
}