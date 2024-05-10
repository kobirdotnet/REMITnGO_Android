package com.bsel.remitngo.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val paymentResponseData: PaymentResponseData?,
    @SerializedName("Message")
    val message: String?
)