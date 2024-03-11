package com.bsel.remitngo.data.repository.payment.dataSource

import com.bsel.remitngo.data.model.encript.EncryptItem
import com.bsel.remitngo.data.model.encript.EncryptResponseItem
import com.bsel.remitngo.data.model.payment.PaymentItem
import com.bsel.remitngo.data.model.payment.PaymentResponseItem
import com.bsel.remitngo.data.model.payment.PaymentStatusResponse
import retrofit2.Response

interface PaymentRemoteDataSource {

    suspend fun payment(paymentItem: PaymentItem): Response<PaymentResponseItem>

    suspend fun paymentStatus(transactionCode: String): Response<PaymentStatusResponse>
    suspend fun encrypt(encryptItem: EncryptItem): Response<EncryptResponseItem>

}





