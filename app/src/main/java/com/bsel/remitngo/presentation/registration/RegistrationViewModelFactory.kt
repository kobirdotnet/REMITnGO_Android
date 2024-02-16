package com.bsel.remitngo.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.RegistrationUseCase

class RegistrationViewModelFactory(
    private val registrationUseCase: RegistrationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(registrationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}