package com.devkanhaiya.bookreader.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.devkanhaiya.bookreader.R
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.databinding.SplashActivityBinding
import com.devkanhaiya.bookreader.di.component.ActivityComponent
import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    lateinit var binding: SplashActivityBinding

    override fun findFragmentPlaceHolder(): Int = 0

    override fun findContentView(): Int = R.layout.splash_activity

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun bindViewWithViewBinding(view: View) {
        binding = SplashActivityBinding.bind(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveToAuthActivity()
    }

    private fun moveToAuthActivity() {
        Handler(Looper.myLooper()!!).postDelayed({
            loadActivity(HomeMainActivity::class.java).byFinishingCurrent().start()
        }, 2000)


    }

}