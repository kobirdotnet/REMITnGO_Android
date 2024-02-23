package com.bsel.remitngo.presentation.di.calculation

import com.bsel.remitngo.domain.useCase.CalculationUseCase
import com.bsel.remitngo.presentation.ui.main.CalculationViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CalculationModule {
    @CalculationScope
    @Provides
    fun provideCalculationViewModelFactory(calculationUseCase: CalculationUseCase): CalculationViewModelFactory {
        return CalculationViewModelFactory(calculationUseCase)
    }
}