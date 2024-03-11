package com.bsel.remitngo.presentation.di.registration

import com.bsel.remitngo.bottomSheet.MarketingBottomSheet
import com.bsel.remitngo.presentation.ui.registration.RegistrationActivity
import dagger.Subcomponent

@RegistrationScope
@Subcomponent(modules = [RegistrationModule::class])
interface RegistrationSubComponent {
    fun inject(registrationActivity: RegistrationActivity)
    fun inject(marketingBottomSheet: MarketingBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationSubComponent
    }
}
