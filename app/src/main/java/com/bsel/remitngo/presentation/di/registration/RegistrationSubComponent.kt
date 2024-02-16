package com.bsel.remitngo.presentation.di.registration

import com.bsel.remitngo.presentation.registration.RegistrationActivity
import dagger.Subcomponent

@RegistrationScope
@Subcomponent(modules = [RegistrationModule::class])
interface RegistrationSubComponent {
    fun inject(registrationActivity: RegistrationActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): RegistrationSubComponent
    }
}
