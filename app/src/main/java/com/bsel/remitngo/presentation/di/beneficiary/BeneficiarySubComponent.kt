package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.bottomSheet.ReasonBottomSheet
import com.bsel.remitngo.bottomSheet.RelationBottomSheet
import com.bsel.remitngo.presentation.ui.beneficiary.BeneficiaryManagementFragment
import com.bsel.remitngo.presentation.ui.beneficiary.SaveRecipientFragment
import com.bsel.remitngo.presentation.ui.beneficiary.ChooseRecipientFragment
import com.bsel.remitngo.presentation.ui.beneficiary.SaveBeneficiaryFragment
import dagger.Subcomponent

@BeneficiaryScope
@Subcomponent(modules = [BeneficiaryModule::class])
interface BeneficiarySubComponent {

    fun inject(beneficiaryManagementFragment: BeneficiaryManagementFragment)
    fun inject(saveBeneficiaryFragment: SaveBeneficiaryFragment)
    fun inject(chooseRecipientFragment: ChooseRecipientFragment)
    fun inject(saveRecipientFragment: SaveRecipientFragment)
    fun inject(relationBottomSheet: RelationBottomSheet)
    fun inject(reasonBottomSheet: ReasonBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BeneficiarySubComponent
    }

}
