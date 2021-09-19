package com.devkanhaiya.bookreader.data.datasource

import com.devkanhaiya.bookreader.data.pojo.DataWrapper
import com.devkanhaiya.bookreader.data.pojo.User
import com.devkanhaiya.bookreader.data.repository.UserRepository
import com.devkanhaiya.bookreader.data.service.AuthenticationService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLiveDataSource @Inject constructor(private val authenticationService: AuthenticationService) : BaseDataSource(), UserRepository {

    override fun login(phone: String): Single<DataWrapper<User>> {
        return execute(authenticationService.login(phone))
    }


}
