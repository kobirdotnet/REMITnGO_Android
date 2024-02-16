package com.bsel.remitngo.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bsel.remitngo.domain.useCase.RegistrationUseCase

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase) : ViewModel() {
    fun getRegistrationData() = liveData {
        val registrationData = registrationUseCase.execute()
        emit(registrationData)
    }
}

