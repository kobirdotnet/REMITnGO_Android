package com.bsel.remitngo.data.model.emp


import com.google.gson.annotations.SerializedName

data class EmpItem(
    @SerializedName("status")
    val status: String?,
    @SerializedName("uniqueId")
    val uniqueId: String?,
    @SerializedName("transactionId")
    val transactionId: String?,
    @SerializedName("consumerId")
    val consumerId: String?,
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("redirectUrl")
    val redirectUrl: String?,
    @SerializedName("channel")
    val channel: String?,
)