package com.bsel.remitngo.presentation.di.support

import com.bsel.remitngo.domain.useCase.SupportUseCase
import com.bsel.remitngo.presentation.ui.support.SupportViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SupportModule {
    @SupportScope
    @Provides
    fun provideSupportViewModelFactory(supportUseCase: SupportUseCase): SupportViewModelFactory {
        return SupportViewModelFactory(supportUseCase)
    }
}