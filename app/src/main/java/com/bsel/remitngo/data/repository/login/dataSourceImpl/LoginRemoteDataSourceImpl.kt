package com.bsel.remitngo.data.repository.login.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import retrofit2.Response

class LoginRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    LoginRemoteDataSource {
    override suspend fun loginUser(loginItem: LoginItem): Response<LoginResponseItem> {
        return remitNgoService.loginUser(loginItem)
    }
}







