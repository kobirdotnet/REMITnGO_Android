package com.bsel.remitngo.presentation.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.PaymentUseCase

class PaymentViewModelFactory(
    private val paymentUseCase: PaymentUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(paymentUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}