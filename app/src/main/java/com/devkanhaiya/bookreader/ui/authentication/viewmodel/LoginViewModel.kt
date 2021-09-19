package com.devkanhaiya.bookreader.ui.authentication.viewmodel

import com.devkanhaiya.bookreader.data.pojo.User
import com.devkanhaiya.bookreader.data.repository.UserRepository
import com.devkanhaiya.bookreader.ui.base.APILiveData
import com.devkanhaiya.bookreader.ui.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    val loginLiveData = APILiveData<User>()

    fun login(phoneNumber: String) {

        userRepository.login(phoneNumber)
                .subscribe(withLiveData(loginLiveData))
    }
}