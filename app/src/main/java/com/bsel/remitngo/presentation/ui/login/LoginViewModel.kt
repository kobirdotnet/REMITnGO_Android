package com.bsel.remitngo.presentation.ui.login

import androidx.lifecycle.*
import com.bsel.remitngo.data.model.forgotPassword.*
import com.bsel.remitngo.data.model.login.LoginItem
import com.bsel.remitngo.data.model.login.LoginResponseItem
import com.bsel.remitngo.domain.useCase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponseItem?>()
    val loginResult: LiveData<LoginResponseItem?> = _loginResult

    fun loginUser(loginItem: LoginItem) {
        viewModelScope.launch {
            val result = loginUseCase.execute(loginItem)
            _loginResult.value = result
        }
    }

    private val _forgotPasswordResult = MutableLiveData<ForgotPasswordResponseItem?>()
    val forgotPasswordResult: LiveData<ForgotPasswordResponseItem?> = _forgotPasswordResult

    fun forgotPassword(forgotPasswordItem: ForgotPasswordItem) {
        viewModelScope.launch {
            val result = loginUseCase.execute(forgotPasswordItem)
            _forgotPasswordResult.value = result
        }
    }

    private val _otpValidationResult = MutableLiveData<OtpValidationResponseItem?>()
    val otpValidationResult: LiveData<OtpValidationResponseItem?> = _otpValidationResult

    fun otpValidation(otpValidationItem: OtpValidationItem) {
        viewModelScope.launch {
            val result = loginUseCase.execute(otpValidationItem)
            _otpValidationResult.value = result
        }
    }

    private val _setPasswordResult = MutableLiveData<SetPasswordResponseItem?>()
    val setPasswordResult: LiveData<SetPasswordResponseItem?> = _setPasswordResult

    fun setPassword(setPasswordItem: SetPasswordItem) {
        viewModelScope.launch {
            val result = loginUseCase.execute(setPasswordItem)
            _setPasswordResult.value = result
        }
    }

}


