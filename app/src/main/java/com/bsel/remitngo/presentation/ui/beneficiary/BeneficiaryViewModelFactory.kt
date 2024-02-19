package com.bsel.remitngo.presentation.ui.beneficiary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.BeneficiaryUseCase

class BeneficiaryViewModelFactory(
    private val beneficiaryUseCase: BeneficiaryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeneficiaryViewModel::class.java)) {
            return BeneficiaryViewModel(beneficiaryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}