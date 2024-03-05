package com.bsel.remitngo.presentation.di.cancel_request

import com.bsel.remitngo.domain.useCase.CancelRequestUseCase
import com.bsel.remitngo.presentation.ui.cancel_request.CancelRequestViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class CancelRequestModule {
    @CancelRequestScope
    @Provides
    fun provideCancelRequestViewModelFactory(cancelRequestUseCase: CancelRequestUseCase): CancelRequestViewModelFactory {
        return CancelRequestViewModelFactory(cancelRequestUseCase)
    }
}