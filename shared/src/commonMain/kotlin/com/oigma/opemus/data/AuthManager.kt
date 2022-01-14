package com.oigma.opemus.data

import com.oigma.opemus.data.models.User
import com.oigma.opemus.data.services.Services
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
interface AuthManager : BaseManager {
    val user: Flow<User>
    fun login()
    fun logout()
}

class AuthManagerImpl(private val services: Services) : BasicManager(), AuthManager {

    override val user: Flow<User>
        get() = _user

    private val _user: MutableStateFlow<User> = MutableStateFlow(User.guest)

    override fun login() {
        execute({
           services.auth.login()
        }, {
            _user.value = it
        })

    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}