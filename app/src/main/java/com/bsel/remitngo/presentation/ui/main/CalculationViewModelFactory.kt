package com.bsel.remitngo.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.CalculationUseCase

class CalculationViewModelFactory(
    private val calculationUseCase: CalculationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculationViewModel::class.java)) {
            return CalculationViewModel(calculationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}