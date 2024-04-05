package com.bsel.remitngo.data.repository.login.dataSource

import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem

import retrofit2.Response

interface LoginRemoteDataSource {
    suspend fun loginUser(loginItem: LoginItem): Response<LoginResponseItem>
    suspend fun forgotPassword(forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem>
    suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem>
    suspend fun setPassword(setPasswordItem: SetPasswordItem): Response<SetPasswordResponseItem>
}





