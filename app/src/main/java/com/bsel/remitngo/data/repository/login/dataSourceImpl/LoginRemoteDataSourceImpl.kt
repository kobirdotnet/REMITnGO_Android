package com.bsel.remitngo.data.repository.login.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.data.repository.login.dataSource.LoginRemoteDataSource
import retrofit2.Response

class LoginRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    LoginRemoteDataSource {
    override suspend fun loginUser(loginItem: LoginItem): Response<LoginResponseItem> {
        return remitNgoService.loginUser(loginItem)
    }
    override suspend fun forgotPassword(forgotPasswordItem: ForgotPasswordItem): Response<ForgotPasswordResponseItem> {
        return remitNgoService.forgotPassword(forgotPasswordItem)
    }

    override suspend fun otpValidation(otpValidationItem: OtpValidationItem): Response<OtpValidationResponseItem> {
        return remitNgoService.otpValidation(otpValidationItem)
    }

    override suspend fun setPassword(setPasswordItem: SetPasswordItem): Response<SetPasswordResponseItem> {
        return remitNgoService.setPassword(setPasswordItem)
    }
}







