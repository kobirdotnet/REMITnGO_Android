package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateData(
    @SerializedName("Amount")
    val amount: Double?,
    @SerializedName("Commission")
    val commission: Double?,
    @SerializedName("FromCurrencyCode")
    val fromCurrencyCode: String?,
    @SerializedName("FromCurrencyId")
    val fromCurrencyId: Int?,
    @SerializedName("Msg")
    val msg: Any?,
    @SerializedName("OrderTypeId")
    val orderTypeId: Int?,
    @SerializedName("PayeeBank")
    val payeeBank: String?,
    @SerializedName("PaymentMode")
    val paymentMode: Int?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("ToCurrencyCode")
    val toCurrencyCode: String?,
    @SerializedName("ToCurrencyId")
    val toCurrencyId: Int?
)