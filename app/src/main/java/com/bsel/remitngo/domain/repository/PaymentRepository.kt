package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse

interface PaymentRepository {

    suspend fun payment(paymentItem: PaymentItem): PaymentResponseItem?
    suspend fun paymentStatus(transactionCode: String): PaymentStatusResponse?
    suspend fun encrypt(encryptItem: EncryptItem): EncryptResponseItem?

}

