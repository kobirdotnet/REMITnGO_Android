package com.bsel.remitngo.data.repository.payment.dataSource

import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import retrofit2.Response

interface PaymentRemoteDataSource {

    suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem>

}





