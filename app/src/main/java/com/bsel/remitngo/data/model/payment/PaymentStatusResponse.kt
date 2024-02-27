package com.bsel.remitngo.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentStatusResponse(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)