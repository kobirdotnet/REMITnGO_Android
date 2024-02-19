package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.domain.useCase.BeneficiaryUseCase
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class BeneficiaryModule {
    @BeneficiaryScope
    @Provides
    fun provideBeneficiaryViewModelFactory(beneficiaryUseCase: BeneficiaryUseCase): BeneficiaryViewModelFactory {
        return BeneficiaryViewModelFactory(beneficiaryUseCase)
    }
}