package com.bsel.remitngo.presentation.ui.support

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.SupportUseCase

class SupportViewModelFactory(
    private val supportUseCase: SupportUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupportViewModel::class.java)) {
            return SupportViewModel(supportUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}