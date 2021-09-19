package com.devkanhaiya.bookreader.di.component


import com.devkanhaiya.bookreader.di.module.ActivityModule
import com.devkanhaiya.bookreader.di.module.FragmentModule
import com.devkanhaiya.bookreader.ui.base.BaseActivity
import com.devkanhaiya.bookreader.ui.home.HomeMainActivity
import com.devkanhaiya.bookreader.ui.isolated.IsolatedActivity
import com.devkanhaiya.bookreader.ui.manager.Navigator
import com.devkanhaiya.bookreader.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

/**
 * Created by hlink21 on 9/5/16.
 */
@com.devkanhaiya.bookreader.di.PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun activity(): BaseActivity

    fun navigator(): Navigator


    operator fun plus(fragmentModule: FragmentModule): FragmentComponent

    fun inject(authMainActivity: SplashActivity)
    fun inject(homeMainActivity: HomeMainActivity)
    fun inject(isolatedActivity: IsolatedActivity)


    @Component.Builder
    interface Builder {

        fun bindApplicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun bindActivity(baseActivity: BaseActivity): Builder

        fun build(): ActivityComponent
    }
}
