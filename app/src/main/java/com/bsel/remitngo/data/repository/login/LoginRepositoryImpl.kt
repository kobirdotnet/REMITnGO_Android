package com.bsel.remitngo.data.repository.login

import android.util.Log
import com.bsel.remitngo.data.model.forgotPassword.*
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

    override suspend fun forgotPassword(forgotPasswordItem: ForgotPasswordItem): ForgotPasswordResponseItem? {
        return try {
            val response = loginRemoteDataSource.forgotPassword(forgotPasswordItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to forgotPassword user: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error forgotPassword user: ${exception.message}", exception)
            null
        }
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return try {
            val response = loginRemoteDataSource.otpValidation(otpValidationItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to otpValidation: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error otpValidation: ${exception.message}", exception)
            null
        }
    }

    override suspend fun setPassword(setPasswordItem: SetPasswordItem): SetPasswordResponseItem? {
        return try {
            val response = loginRemoteDataSource.setPassword(setPasswordItem)
            if (response.isSuccessful) {
                response.body()
            } else {
                // Handle server error or invalid response
                Log.e("MyTag", "Failed to setPassword: ${response.code()}")
                null
            }
        } catch (exception: Exception) {
            // Handle network or unexpected errors
            Log.e("MyTag", "Error setPassword: ${exception.message}", exception)
            null
        }
    }
}
