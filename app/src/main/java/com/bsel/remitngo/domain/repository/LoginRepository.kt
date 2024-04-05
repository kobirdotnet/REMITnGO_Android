package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem

interface LoginRepository {
    suspend fun loginUser(loginItem: LoginItem): LoginResponseItem?
    suspend fun forgotPassword(forgotPasswordItem: ForgotPasswordItem): ForgotPasswordResponseItem?
    suspend fun otpValidation(otpValidationItem: OtpValidationItem): OtpValidationResponseItem?
    suspend fun setPassword(setPasswordItem: SetPasswordItem): SetPasswordResponseItem?
}

