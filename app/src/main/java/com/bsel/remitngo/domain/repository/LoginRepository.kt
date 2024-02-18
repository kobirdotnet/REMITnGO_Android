package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem

interface LoginRepository {
    suspend fun loginUser(loginItem: LoginItem): LoginResponseItem?
}

