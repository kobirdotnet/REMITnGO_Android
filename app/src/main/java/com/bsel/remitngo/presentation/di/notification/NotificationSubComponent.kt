package com.bsel.remitngo.presentation.di.notification

import com.bsel.remitngo.presentation.ui.notification.NotificationFragment
import dagger.Subcomponent

@NotificationScope
@Subcomponent(modules = [NotificationModule::class])
interface NotificationSubComponent {

    fun inject(notificationFragment: NotificationFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): NotificationSubComponent
    }
}
