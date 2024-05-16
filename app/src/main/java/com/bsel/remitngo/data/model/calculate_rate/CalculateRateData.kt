package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateData(
    @SerializedName("BeneficiaryAmount")
    val beneficiaryAmount: Double?,
    @SerializedName("Commission")
    val commission: Double?,
    @SerializedName("FromCurrencyCode")
    val fromCurrencyCode: String?,
    @SerializedName("FromCurrencyId")
    val fromCurrencyId: Int?,
    @SerializedName("Msg")
    val msg: String?,
    @SerializedName("OrderTypeId")
    val orderTypeId: Int?,
    @SerializedName("PayeeBank")
    val payeeBank: String?,
    @SerializedName("PaymentMode")
    val paymentMode: Int?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SenderAmount")
    val senderAmount: Double?,
    @SerializedName("ToCurrencyCode")
    val toCurrencyCode: String?,
    @SerializedName("ToCurrencyId")
    val toCurrencyId: Int?,
    @SerializedName("TotalAmount")
    val totalAmount: Double?
)