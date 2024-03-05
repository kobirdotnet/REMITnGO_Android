package com.bsel.remitngo.presentation.ui.cancel_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.CancelRequestUseCase

class CancelRequestViewModelFactory(
    private val cancelRequestUseCase: CancelRequestUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CancelRequestViewModel::class.java)) {
            return CancelRequestViewModel(cancelRequestUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}