package com.devkanhaiya.bookreader.di.module

import com.devkanhaiya.bookreader.ui.base.BaseFragment
import dagger.Module
import dagger.Provides

/**
 * Created by hlink21 on 31/5/16.
 */
@Module
class FragmentModule(private val baseFragment: BaseFragment) {

    @Provides
    @com.devkanhaiya.bookreader.di.PerFragment
    internal fun provideBaseFragment(): BaseFragment {
        return baseFragment
    }

}
