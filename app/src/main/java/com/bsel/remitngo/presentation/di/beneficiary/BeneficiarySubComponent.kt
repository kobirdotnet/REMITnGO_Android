package com.bsel.remitngo.presentation.di.beneficiary

import com.bsel.remitngo.presentation.ui.beneficiary.RecipientDetailsFragment
import dagger.Subcomponent

@BeneficiaryScope
@Subcomponent(modules = [BeneficiaryModule::class])
interface BeneficiarySubComponent {

    fun inject(recipientDetailsFragment: RecipientDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): BeneficiarySubComponent
    }

}
