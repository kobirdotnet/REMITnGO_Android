package com.bsel.remitngo.data.api

object TokenManager {
    private var authToken: String? = null

    fun setToken(token: String?) {
        authToken = token
    }

    fun getToken(): String? {
        return authToken
    }
}
