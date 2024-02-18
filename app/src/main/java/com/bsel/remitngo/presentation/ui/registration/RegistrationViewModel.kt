package com.bsel.remitngo.presentation.ui.registration

import androidx.lifecycle.*
import com.bsel.remitngo.data.model.registration.RegistrationItem
import com.bsel.remitngo.data.model.registration.RegistrationResponseItem
import com.bsel.remitngo.domain.useCase.RegistrationUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase) : ViewModel() {

    private val _registrationResult = MutableLiveData<RegistrationResponseItem?>()
    val registrationResult: LiveData<RegistrationResponseItem?> = _registrationResult

    fun registerUser(registrationItem: RegistrationItem) {
        viewModelScope.launch {
            val result = registrationUseCase.execute(registrationItem)
            _registrationResult.value = result
        }
    }

}


