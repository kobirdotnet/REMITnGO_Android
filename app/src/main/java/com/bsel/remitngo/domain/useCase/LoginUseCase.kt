package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.domain.repository.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun execute(loginItem: LoginItem): LoginResponseItem? {
        return loginRepository.loginUser(loginItem)
    }
    suspend fun execute(forgotPasswordItem: ForgotPasswordItem): ForgotPasswordResponseItem? {
        return loginRepository.forgotPassword(forgotPasswordItem)
    }
    suspend fun execute(otpValidationItem: OtpValidationItem): OtpValidationResponseItem? {
        return loginRepository.otpValidation(otpValidationItem)
    }
    suspend fun execute(setPasswordItem: SetPasswordItem): SetPasswordResponseItem? {
        return loginRepository.setPassword(setPasswordItem)
    }
}

