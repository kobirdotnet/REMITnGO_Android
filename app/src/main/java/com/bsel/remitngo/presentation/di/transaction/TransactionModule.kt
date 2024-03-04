package com.bsel.remitngo.presentation.di.transaction

import com.bsel.remitngo.domain.useCase.TransactionUseCase
import com.bsel.remitngo.presentation.ui.transaction.TransactionViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TransactionModule {
    @TransactionScope
    @Provides
    fun provideTransactionViewModelFactory(transactionUseCase: TransactionUseCase): TransactionViewModelFactory {
        return TransactionViewModelFactory(transactionUseCase)
    }
}