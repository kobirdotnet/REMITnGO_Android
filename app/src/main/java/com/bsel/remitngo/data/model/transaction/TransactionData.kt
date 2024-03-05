package com.bsel.remitngo.data.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("BenName")
    val benName: String?,
    @SerializedName("CurrencyFromAmount")
    val currencyFromAmount: String?,
    @SerializedName("CurrencyToAmount")
    val currencyToAmount: String?,
    @SerializedName("BenAmount")
    val benAmount: Double?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("TransferFees")
    val transferFees: Double?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?,
    @SerializedName("TransferStatus")
    val transferStatus: String?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("Id")
    val Id: Int?,
    @SerializedName("TotalAmount")
    val totalAmount: Double?,
    @SerializedName("PaymentMode")
    val paymentMode: String?,
    @SerializedName("PayID")
    val payId: Int?,
    @SerializedName("CustomerName")
    val customerName: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("LiveStatus")
    val liveStatus: String?
)