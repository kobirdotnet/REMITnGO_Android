package com.bsel.remitngo.presentation.di

import com.bsel.remitngo.presentation.di.bank.BankSubComponent
import com.bsel.remitngo.presentation.di.beneficiary.BeneficiarySubComponent
import com.bsel.remitngo.presentation.di.calculation.CalculationSubComponent
import com.bsel.remitngo.presentation.di.cancel_request.CancelRequestSubComponent
import com.bsel.remitngo.presentation.di.document.DocumentSubComponent
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.payment.PaymentSubComponent
import com.bsel.remitngo.presentation.di.profile.ProfileSubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent
import com.bsel.remitngo.presentation.di.transaction.TransactionSubComponent

interface Injector {
    fun createRegistrationSubComponent(): RegistrationSubComponent
    fun createLoginSubComponent(): LoginSubComponent
    fun createBeneficiarySubComponent(): BeneficiarySubComponent
    fun createBankSubComponent(): BankSubComponent
    fun createCalculationSubComponent(): CalculationSubComponent
    fun createPaymentSubComponent(): PaymentSubComponent
    fun createProfileSubComponent(): ProfileSubComponent
    fun createDocumentSubComponent(): DocumentSubComponent
    fun createTransactionSubComponent(): TransactionSubComponent
    fun createCancelRequestSubComponent(): CancelRequestSubComponent
}