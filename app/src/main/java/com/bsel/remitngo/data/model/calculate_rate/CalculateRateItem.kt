package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("bankId")
    val bankId: Int?,
    @SerializedName("payingAgentId")
    val payingAgentId: Int?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("paymentMode")
    val paymentMode: Int?,
    @SerializedName("fromCountry")
    val fromCountry: Int?,
    @SerializedName("toCountry")
    val toCountry: Int?,
    @SerializedName("mobileOrWebPlatform")
    val mobileOrWebPlatform: Int?,
    @SerializedName("amount")
    val amount: String?,
)