package com.bsel.remitngo.presentation.di.calculation

import com.bsel.remitngo.bottomSheet.PayingAgentBankBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentWalletBottomSheet
import com.bsel.remitngo.presentation.ui.main.MainFragment
import dagger.Subcomponent

@CalculationScope
@Subcomponent(modules = [CalculationModule::class])
interface CalculationSubComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(payingAgentBankBottomSheet: PayingAgentBankBottomSheet)
    fun inject(payingAgentWalletBottomSheet: PayingAgentWalletBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CalculationSubComponent
    }

}
