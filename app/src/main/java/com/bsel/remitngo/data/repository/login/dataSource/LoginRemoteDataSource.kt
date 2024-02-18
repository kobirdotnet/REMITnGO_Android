package com.bsel.remitngo.data.repository.login.dataSource

import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem

import retrofit2.Response

interface LoginRemoteDataSource {
    suspend fun loginUser(loginItem: LoginItem): Response<LoginResponseItem>
}





