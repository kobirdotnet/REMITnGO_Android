package com.bsel.remitngo.presentation.ui.bank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.BankUseCase

class BankViewModelFactory(
    private val bankUseCase: BankUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BankViewModel::class.java)) {
            return BankViewModel(bankUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}