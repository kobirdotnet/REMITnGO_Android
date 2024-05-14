package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateItem(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("bankId")
    val bankId: Int?,
    @SerializedName("calculationType")
    val calculationType: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("fromCountry")
    val fromCountry: Int?,
    @SerializedName("mobileOrWebPlatform")
    val mobileOrWebPlatform: Int?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("payingAgentId")
    val payingAgentId: Int?,
    @SerializedName("paymentMode")
    val paymentMode: Int?,
    @SerializedName("toCountry")
    val toCountry: Int?
)