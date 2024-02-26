package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem

interface PaymentRepository {

    suspend fun payment(paymentItem: PaymentItem): PaymentResponseItem?

}

