package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.bottomSheet.ChooseRecipientBottomSheet
import com.bsel.remitngo.bottomSheet.PurposeOfTransferBottomSheet
import com.bsel.remitngo.bottomSheet.SaveRecipientBottomSheet
import com.bsel.remitngo.presentation.ui.beneficiary.beneficiaryManagement.BeneficiaryManagementFragment
import com.bsel.remitngo.presentation.ui.beneficiary.recipientManagement.SaveRecipientFragment
import com.bsel.remitngo.presentation.ui.beneficiary.recipientManagement.ChooseRecipientFragment
import com.bsel.remitngo.presentation.ui.beneficiary.beneficiaryManagement.SaveBeneficiaryFragment
import dagger.Subcomponent

@BeneficiaryScope
@Subcomponent(modules = [BeneficiaryModule::class])
interface BeneficiarySubComponent {

    fun inject(beneficiaryManagementFragment: BeneficiaryManagementFragment)
    fun inject(saveBeneficiaryFragment: SaveBeneficiaryFragment)
    fun inject(chooseRecipientFragment: ChooseRecipientFragment)
    fun inject(saveRecipientFragment: SaveRecipientFragment)
    fun inject(purposeOfTransferBottomSheet: PurposeOfTransferBottomSheet)
    fun inject(chooseRecipientBottomSheet: ChooseRecipientBottomSheet)
    fun inject(saveRecipientBottomSheet: SaveRecipientBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BeneficiarySubComponent
    }

}
