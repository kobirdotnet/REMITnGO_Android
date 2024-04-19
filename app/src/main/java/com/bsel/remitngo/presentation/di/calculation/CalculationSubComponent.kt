package com.bsel.remitngo.presentation.di.calculation

import com.bsel.remitngo.bottomSheet.PayingAgentCashPickupBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentInstantCreditBottomSheet
import com.bsel.remitngo.bottomSheet.PayingAgentWalletBottomSheet
import com.bsel.remitngo.presentation.ui.main.MainFragment
import dagger.Subcomponent

@CalculationScope
@Subcomponent(modules = [CalculationModule::class])
interface CalculationSubComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(payingAgentInstantCreditBottomSheet: PayingAgentInstantCreditBottomSheet)
    fun inject(payingAgentCashPickupBottomSheet: PayingAgentCashPickupBottomSheet)
    fun inject(payingAgentWalletBottomSheet: PayingAgentWalletBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CalculationSubComponent
    }

}
