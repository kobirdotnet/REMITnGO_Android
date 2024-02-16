package com.bsel.remitngo.presentation.di

import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent

interface Injector {
    fun createRegistrationSubComponent(): RegistrationSubComponent
}