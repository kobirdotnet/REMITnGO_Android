package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.bottom_sheet.ReasonBottomSheet
import com.bsel.remitngo.bottom_sheet.RelationBottomSheet
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryFragment
import dagger.Subcomponent

@BeneficiaryScope
@Subcomponent(modules = [BeneficiaryModule::class])
interface BeneficiarySubComponent {

    fun inject(beneficiaryFragment: BeneficiaryFragment)
    fun inject(relationBottomSheet: RelationBottomSheet)
    fun inject(reasonBottomSheet: ReasonBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BeneficiarySubComponent
    }

}
