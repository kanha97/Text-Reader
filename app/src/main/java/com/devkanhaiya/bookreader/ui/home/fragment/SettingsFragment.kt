package com.devkanhaiya.bookreader.ui.home.fragment

import android.view.View
import android.widget.Toast
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

                (activity as HomeMainActivity).showBackSymbol(true)
    }

    private fun setUpRecyclerView() {
        list = arrayListOf(
            getString(R.string.label_change_password),
            getString(R.string.title_notification),
            getString(R.string.setting_view_right),
            getString(R.string.title_about_us),
            getString(R.string.title_contact_us),
            getString(R.string.title_terms_condition),
            getString(R.string.title_privacy_policy),
            getString(R.string.title_faqs),
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
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    ChangePasswordFragment::class.java
                ).start()
            }

            1 -> {
                appPreference.putBoolean(
                    Const.SETTINGS_NOTIFICATION,
                    settingsAdapter.getNotification()
                )
            }
            3 -> {
                navigator.loadActivity(IsolatedActivity::class.java, AboutUsFragment::class.java)
                    .start()
            }
            4 -> {
                navigator.loadActivity(IsolatedActivity::class.java, GetInTouchFragment::class.java)
                    .start()
            }
            5 -> {
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    TermsNConditionsFragment::class.java
                ).start()
            }
            6 -> {
                navigator.loadActivity(
                    IsolatedActivity::class.java,
                    PrivacyNPolicyFragment::class.java
                ).start()
            }
            7 -> {
                navigator.loadActivity(IsolatedActivity::class.java, FAQsFragment::class.java)
                    .start()
            }
            8 -> {
                ///dummy
                Toast.makeText(context, getString(R.string.title_rate_app), Toast.LENGTH_SHORT)
                    .show()

            }
            9 -> {
                ////dummy
                Toast.makeText(context, getString(R.string.title_share_app), Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as HomeMainActivity).showBackSymbol(false)
    }
}