package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import com.bsel.remitngo.domain.repository.PaymentRepository

class PaymentUseCase(private val paymentRepository: PaymentRepository) {

    suspend fun execute(paymentItem: PaymentItem): PaymentResponseItem? {
        return paymentRepository.payment(paymentItem)
    }

    suspend fun execute(transactionCode: String): PaymentStatusResponse? {
        return paymentRepository.paymentStatus(transactionCode)
    }
    suspend fun execute(encryptItem: EncryptItem): EncryptResponseItem? {
        return paymentRepository.encrypt(encryptItem)
    }

}

