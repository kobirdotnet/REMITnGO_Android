package com.bsel.remitngo.presentation.di.notification

import com.bsel.remitngo.domain.useCase.NotificationUseCase
import com.bsel.remitngo.presentation.ui.notification.NotificationViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {
    @NotificationScope
    @Provides
    fun provideNotificationViewModelFactory(notificationUseCase: NotificationUseCase): NotificationViewModelFactory {
        return NotificationViewModelFactory(notificationUseCase)
    }
}