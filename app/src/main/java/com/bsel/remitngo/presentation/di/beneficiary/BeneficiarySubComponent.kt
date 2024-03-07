package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.bottomSheet.ReasonBottomSheet
import com.bsel.remitngo.bottomSheet.RelationBottomSheet
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryFragment
import com.bsel.remitngo.presentation.ui.beneficiary.ChooseBeneficiaryFragment
import dagger.Subcomponent

@BeneficiaryScope
@Subcomponent(modules = [BeneficiaryModule::class])
interface BeneficiarySubComponent {

    fun inject(chooseBeneficiaryFragment: ChooseBeneficiaryFragment)
    fun inject(beneficiaryFragment: BeneficiaryFragment)
    fun inject(relationBottomSheet: RelationBottomSheet)
    fun inject(reasonBottomSheet: ReasonBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BeneficiarySubComponent
    }

}
