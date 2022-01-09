package com.oigma.opemus.data.models

/**
 * Created by Harsewak Singh on 09/01/2022.
 *
 * contains data models related to user auth
 */
data class User(val id: String, val firstName: String, val lastName: String) {
    val name: String = firstName.plus(lastName)
    val role: Role = Role.guest

    companion object {
        val guest = User("guest_id", "", "")
    }
}

data class Role (val role: String) {
    companion object {
        val guest: Role = Role("guest")
    }
}