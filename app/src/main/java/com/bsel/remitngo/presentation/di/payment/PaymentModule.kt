package com.bsel.remitngo.presentation.di.payment

import com.bsel.remitngo.domain.useCase.PaymentUseCase
import com.bsel.remitngo.presentation.ui.payment.PaymentViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PaymentModule {
    @PaymentScope
    @Provides
    fun providePaymentViewModelFactory(paymentUseCase: PaymentUseCase): PaymentViewModelFactory {
        return PaymentViewModelFactory(paymentUseCase)
    }
}