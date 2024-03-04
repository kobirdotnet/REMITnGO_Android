package com.bsel.remitngo.presentation.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.TransactionUseCase

class TransactionViewModelFactory(
    private val transactionUseCase: TransactionUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(transactionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}