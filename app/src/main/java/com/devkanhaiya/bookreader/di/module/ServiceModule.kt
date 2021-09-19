package com.devkanhaiya.bookreader.di.module

import com.devkanhaiya.bookreader.data.datasource.UserLiveDataSource
import com.devkanhaiya.bookreader.data.repository.UserRepository
import com.devkanhaiya.bookreader.data.service.AuthenticationService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class ServiceModule {

    @Provides
    @Singleton
    fun provideUserRepository(userLiveDataSource: UserLiveDataSource): UserRepository {
        return userLiveDataSource
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

}