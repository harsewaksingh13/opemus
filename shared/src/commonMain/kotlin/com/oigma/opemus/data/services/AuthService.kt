package com.oigma.opemus.data.services

import com.oigma.opemus.data.models.User

/**
 * Created by Harsewak Singh on 09/01/2022.
 */
interface AuthService {
    suspend fun login(): User
    suspend fun register(): User
}