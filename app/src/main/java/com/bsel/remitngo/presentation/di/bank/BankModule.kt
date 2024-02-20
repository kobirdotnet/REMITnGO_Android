package com.bsel.remitngo.presentation.di.bank

import com.bsel.remitngo.domain.useCase.BankUseCase
import com.bsel.remitngo.presentation.ui.bank.BankViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class BankModule {
    @BankScope
    @Provides
    fun provideBankViewModelFactory(bankUseCase: BankUseCase): BankViewModelFactory {
        return BankViewModelFactory(bankUseCase)
    }
}