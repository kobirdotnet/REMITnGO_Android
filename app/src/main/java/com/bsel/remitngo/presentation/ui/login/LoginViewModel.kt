package com.bsel.remitngo.presentation.ui.login

import androidx.lifecycle.*
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

}


