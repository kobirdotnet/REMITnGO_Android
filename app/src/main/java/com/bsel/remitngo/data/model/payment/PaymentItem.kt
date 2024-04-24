package com.bsel.remitngo.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentItem(
    @SerializedName("beneAccountName")
    val beneAccountName: String?,
    @SerializedName("beneAccountNo")
    val beneAccountNo: String?,
    @SerializedName("beneAmount")
    val beneAmount: Double?,
    @SerializedName("beneBankId")
    val beneBankId: Int?,
    @SerializedName("beneBranchId")
    val beneBranchId: Int?,
    @SerializedName("beneId")
    val beneId: Int?,
    @SerializedName("beneMobile")
    val beneMobile: String?,
    @SerializedName("benePersonId")
    val benePersonId: Int?,
    @SerializedName("beneWalletId")
    val beneWalletId: Int?,
    @SerializedName("beneWalletNo")
    val beneWalletNo: String?,
    @SerializedName("channelId")
    val channelId: Int?,
    @SerializedName("commission")
    val commission: Double?,
    @SerializedName("fromCountryId")
    val fromCountryId: Int?,
    @SerializedName("fromCurrencyId")
    val fromCurrencyId: Int?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("modifiedBeneAmount")
    val modifiedBeneAmount: Double?,
    @SerializedName("modifiedCommission")
    val modifiedCommission: Double?,
    @SerializedName("modifiedRate")
    val modifiedRate: Double?,
    @SerializedName("modifiedSendAmount")
    val modifiedSendAmount: Double?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("payingAgentId")
    val payingAgentId: Int?,
    @SerializedName("paymentMode")
    val paymentMode: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("promoCode")
    val promoCode: String?,
    @SerializedName("purposeOfTransferId")
    val purposeOfTransferId: Int?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("sendAmount")
    val sendAmount: Double?,
    @SerializedName("sourceOfFundId")
    val sourceOfFundId: Int?,
    @SerializedName("toCountryId")
    val toCountryId: Int?,
    @SerializedName("toCurrencyId")
    val toCurrencyId: Int?,
    @SerializedName("totalAmountGiven")
    val totalAmountGiven: Double?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?
)