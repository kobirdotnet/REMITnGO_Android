package com.bsel.remitngo.presentation.di.settings

import com.bsel.remitngo.presentation.ui.settings.SettingsFragment
import com.bsel.remitngo.presentation.ui.settings.changePassword.ChangePasswordFragment
import dagger.Subcomponent

@SettingsScope
@Subcomponent(modules = [SettingsModule::class])
interface SettingsSubComponent {

    fun inject(settingsFragment: SettingsFragment)
    fun inject(changePasswordFragment: ChangePasswordFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsSubComponent
    }

}
