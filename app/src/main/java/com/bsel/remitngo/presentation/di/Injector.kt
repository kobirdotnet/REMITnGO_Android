package com.bsel.remitngo.presentation.di

import com.bsel.remitngo.presentation.di.bank.BankSubComponent
import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent

interface Injector {
    fun createRegistrationSubComponent(): RegistrationSubComponent
    fun createLoginSubComponent(): LoginSubComponent
    fun createBeneficiarySubComponent(): BeneficiarySubComponent
    fun createBankSubComponent(): BankSubComponent
}