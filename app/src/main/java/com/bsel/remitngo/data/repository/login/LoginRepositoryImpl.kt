package com.bsel.remitngo.data.repository.login

import android.util.Log
import com.bsel.remitngo.domain.repository.LoginRepository
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource

class LoginRepositoryImpl(private val loginRemoteDataSource: LoginRemoteDataSource) : LoginRepository {

    override suspend fun loginUser(loginItem: LoginItem): LoginResponseItem? {
        return try {
            val response = loginRemoteDataSource.loginUser(loginItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to login user: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error login user: ${exception.message}", exception)
            null
        }
    }
}
