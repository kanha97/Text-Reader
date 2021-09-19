package com.devkanhaiya.bookreader.di.component


import com.devkanhaiya.bookreader.di.module.FragmentModule
import com.devkanhaiya.bookreader.ui.base.BaseFragment
import com.devkanhaiya.bookreader.ui.home.fragment.*
import dagger.Subcomponent

/**
 * Created by hlink21 on 31/5/16.
 */

@com.devkanhaiya.bookreader.di.PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun baseFragment(): BaseFragment
    fun inject(homeFragment: HomeFragment)
    fun inject(editProfileFragment: EditProfileFragment)
    fun inject(notifiactionFragment: NotificationFragment)
    fun inject(accountOpenJobsFragment: AccountOpenJobsFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(changePasswordFragment: ChangePasswordFragment)
    fun inject(getInTouchFragment: GetInTouchFragment)
    fun inject(termsNConditionsFragment: TermsNConditionsFragment)
    fun inject(privacyNPolicyFragment: PrivacyNPolicyFragment)
    fun inject(faQsFragment: FAQsFragment)
    fun inject(aboutUsFragment: AboutUsFragment)
    fun inject(orderDetailsFragment: OrderDetailsFragment)
    //   fun inject(loginFragment: LoginFragment)
}
