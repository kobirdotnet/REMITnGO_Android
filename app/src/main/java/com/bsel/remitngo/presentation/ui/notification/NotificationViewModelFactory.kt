package com.bsel.remitngo.presentation.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bsel.remitngo.domain.useCase.NotificationUseCase

class NotificationViewModelFactory(
    private val notificationUseCase: NotificationUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(notificationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}