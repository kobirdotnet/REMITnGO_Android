package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateData(
    @SerializedName("CommissionForBankTransfer")
    val commissionForBankTransfer: Double?,
    @SerializedName("CommissionForCardPayment")
    val commissionForCardPayment: Double?,
    @SerializedName("FromCurrencyCode")
    val fromCurrencyCode: String?,
    @SerializedName("FromCurrencyId")
    val fromCurrencyId: Int?,
    @SerializedName("Msg")
    val msg: String?,
    @SerializedName("PayeeBank")
    val payeeBank: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("ToCurrencyCode")
    val toCurrencyCode: String?,
    @SerializedName("ToCurrencyId")
    val toCurrencyId: Int?
)