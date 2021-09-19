package com.devkanhaiya.bookreader.data.repository

import com.devkanhaiya.bookreader.data.pojo.DataWrapper
import com.devkanhaiya.bookreader.data.pojo.User
import io.reactivex.Single

interface UserRepository {

    fun login(phone: String): Single<DataWrapper<User>>
}