package com.bsel.remitngo.presentation.di.calculation

import com.bsel.remitngo.bottom_sheet.PayingAgentBottomSheet
import com.bsel.remitngo.presentation.ui.main.MainFragment
import dagger.Subcomponent

@CalculationScope
@Subcomponent(modules = [CalculationModule::class])
interface CalculationSubComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(payingAgentBottomSheet: PayingAgentBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CalculationSubComponent
    }

}
