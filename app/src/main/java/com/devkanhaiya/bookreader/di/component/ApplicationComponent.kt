package com.devkanhaiya.bookreader.di.component

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import com.devkanhaiya.bookreader.core.AppPreferences
import com.devkanhaiya.bookreader.data.repository.UserRepository
import com.devkanhaiya.bookreader.di.App
import com.devkanhaiya.bookreader.di.module.ApplicationModule
import com.devkanhaiya.bookreader.di.module.NetModule
import com.devkanhaiya.bookreader.di.module.ServiceModule
import com.devkanhaiya.bookreader.di.module.ViewModelModule
import com.devkanhaiya.bookreader.util.Validator
import dagger.BindsInstance
import dagger.Component
import java.io.File
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by hlink21 on 9/5/16.
 */
@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, NetModule::class, ServiceModule::class])
interface ApplicationComponent {

    fun context(): Context

    @Named("cache")
    fun provideCacheDir(): File

    fun provideResources(): Resources

    fun provideCurrentLocale(): Locale

    fun provideViewModelFactory(): ViewModelProvider.Factory

    fun inject(appShell: App)

    fun provideUserRepository(): UserRepository

    fun provideValidator(): Validator

    fun provideAppPreference(): AppPreferences

    @Component.Builder
    interface ApplicationComponentBuilder {

        @BindsInstance
        fun apiKey(@Named("api-key") apiKey: String): ApplicationComponentBuilder

        @BindsInstance
        fun application(application: Application): ApplicationComponentBuilder

        fun build(): ApplicationComponent
    }

}
