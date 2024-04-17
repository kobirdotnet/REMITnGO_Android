package com.bsel.remitngo.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?,
    @SerializedName("personID")
    val personID: String?,
    @SerializedName("customerName")
    val customerName: String?,
    @SerializedName("customerEmail")
    val customerEmail: String?,
    @SerializedName("customerMobile")
    val customerMobile: String?,
    @SerializedName("customerdateOfBirth")
    val customerdateOfBirth: String?,
    @SerializedName("fromCountryID")
    val fromCountryID: String?,
    @SerializedName("fromCurrencyID")
    val fromCurrencyID: String?,
    @SerializedName("fromCurrencyCode")
    val fromCurrencyCode: String?,
    @SerializedName("benPersonID")
    val benPersonID: String?,
    @SerializedName("beneficiaryName")
    val beneficiaryName: String?,
    @SerializedName("beneficaryEmail")
    val beneficaryEmail: String?,
    @SerializedName("beneficarymobile")
    val beneficarymobile: String?,
    @SerializedName("beneficaryAddress")
    val beneficaryAddress: String?,
    @SerializedName("bankId")
    val bankId: String?,
    @SerializedName("bankName")
    val bankName: String?,
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("benBranchId")
    val benBranchId: String?,
    @SerializedName("collectionBankID")
    val collectionBankID: String?,
    @SerializedName("collectionBankName")
    val collectionBankName: String?,
    @SerializedName("sendAmount")
    val sendAmount: String?,
    @SerializedName("receivableAmount")
    val receivableAmount: String?,
    @SerializedName("rate")
    val rate: String?,
    @SerializedName("commission")
    val commission: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("toCountryID")
    val toCountryID: String?,
    @SerializedName("toCurrencyID")
    val toCurrencyID: String?,
    @SerializedName("toCurrencyCode")
    val toCurrencyCode: String?,
    @SerializedName("orderTypeID")
    val orderTypeID: String?,
    @SerializedName("paymentMode")
    val paymentMode: String?,
    @SerializedName("purposeOfTransferId")
    val purposeOfTransferId: String?,
    @SerializedName("sourceOfFundId")
    val sourceOfFundId: String?,
    @SerializedName("isMobileTransfer")
    val isMobileTransfer: Boolean?,
    @SerializedName("isiOS")
    val isiOS: Boolean?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,

//    @SerializedName("beneAccountName")
//    val beneAccountName: String?,
//    @SerializedName("beneAccountNo")
//    val beneAccountNo: String?,
//    @SerializedName("beneAmount")
//    val beneAmount: Double?,
//    @SerializedName("beneBankId")
//    val beneBankId: Int?,
//    @SerializedName("beneBranchId")
//    val beneBranchId: Int?,
//    @SerializedName("beneId")
//    val beneId: Int?,
//    @SerializedName("beneMobile")
//    val beneMobile: String?,
//    @SerializedName("benePersonId")
//    val benePersonId: Int?,
//    @SerializedName("beneWalletId")
//    val beneWalletId: Int?,
//    @SerializedName("beneWalletNo")
//    val beneWalletNo: String?,
//    @SerializedName("channelId")
//    val channelId: Int?,
//    @SerializedName("fromCountryId")
//    val fromCountryId: Int?,
//    @SerializedName("fromCurrencyId")
//    val fromCurrencyId: Int?,
//    @SerializedName("modifiedBeneAmount")
//    val modifiedBeneAmount: Double?,
//    @SerializedName("modifiedCommission")
//    val modifiedCommission: Double?,
//    @SerializedName("modifiedRate")
//    val modifiedRate: Double?,
//    @SerializedName("modifiedSendAmount")
//    val modifiedSendAmount: Double?,
//    @SerializedName("orderType")
//    val orderType: Int?,
//    @SerializedName("payingAgentId")
//    val payingAgentId: Int?,
//    @SerializedName("personId")
//    val personId: Int?,
//    @SerializedName("promoCode")
//    val promoCode: String?,
//    @SerializedName("toCountryId")
//    val toCountryId: Int?,
//    @SerializedName("toCurrencyId")
//    val toCurrencyId: Int?,
//    @SerializedName("totalAmountGiven")
//    val totalAmountGiven: Double?,


)