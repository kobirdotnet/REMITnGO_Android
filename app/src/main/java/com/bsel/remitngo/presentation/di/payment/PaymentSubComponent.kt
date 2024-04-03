package com.bsel.remitngo.presentation.di.payment

import com.bsel.remitngo.presentation.ui.payment.PaymentBankFragment
import com.bsel.remitngo.presentation.ui.payment.PaymentCardFragment
import com.bsel.remitngo.presentation.ui.payment.PaymentFragment
import dagger.Subcomponent

@PaymentScope
@Subcomponent(modules = [PaymentModule::class])
interface PaymentSubComponent {

    fun inject(paymentFragment: PaymentFragment)
    fun inject(paymentBankFragment: PaymentBankFragment)
    fun inject(paymentCardFragment: PaymentCardFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PaymentSubComponent
    }

}
