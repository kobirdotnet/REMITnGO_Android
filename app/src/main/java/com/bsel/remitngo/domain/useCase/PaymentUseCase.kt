package com.bsel.remitngo.domain.useCase

import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.domain.repository.PaymentRepository

class PaymentUseCase(private val paymentRepository: PaymentRepository) {

    suspend fun execute(paymentItem: PaymentItem): PaymentResponseItem? {
        return paymentRepository.payment(paymentItem)
    }

}

