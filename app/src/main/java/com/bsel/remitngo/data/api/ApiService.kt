package com.bsel.remitngo.data.api

import com.bsel.remitngo.data.model.createReceipt.CreateReceiptResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("Receipt/CreateReceipt")
    suspend fun createReceipt(@Query("TransactionId") transactionId: String): Response<CreateReceiptResponse>
}
