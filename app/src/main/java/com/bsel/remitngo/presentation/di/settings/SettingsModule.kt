package com.bsel.remitngo.presentation.di.settings

import com.bsel.remitngo.domain.useCase.SettingsUseCase
import com.bsel.remitngo.presentation.ui.settings.SettingsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {
    @SettingsScope
    @Provides
    fun provideSettingsViewModelFactory(settingsUseCase: SettingsUseCase): SettingsViewModelFactory {
        return SettingsViewModelFactory(settingsUseCase)
    }
}