package com.bsel.remitngo.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponseData(
    @SerializedName("TransactionCode")
    val transactionCode: String?
)