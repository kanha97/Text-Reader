package com.devkanhaiya.bookreader.di.module

import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.manager.FragmentHandler
import com.devkanhaiya.bookreader.ui.manager.Navigator

import javax.inject.Named

import dagger.Module
import dagger.Provides

/**
 * Created by hlink21 on 9/5/16.
 */
@Module
class ActivityModule {

    @Provides
    @com.devkanhaiya.bookreader.di.PerActivity
    internal fun navigator(activity: BaseActivity): Navigator {
        return activity
    }

    @Provides
    @com.devkanhaiya.bookreader.di.PerActivity
    internal fun fragmentManager(baseActivity: BaseActivity): androidx.fragment.app.FragmentManager {
        return baseActivity.supportFragmentManager
    }

    @Provides
    @com.devkanhaiya.bookreader.di.PerActivity
    @Named("placeholder")
    internal fun placeHolder(baseActivity: BaseActivity): Int {
        return baseActivity.findFragmentPlaceHolder()
    }

    @Provides
    @com.devkanhaiya.bookreader.di.PerActivity
    internal fun fragmentHandler(fragmentManager: com.devkanhaiya.bookreader.ui.manager.FragmentManager): FragmentHandler {
        return fragmentManager
    }


}
