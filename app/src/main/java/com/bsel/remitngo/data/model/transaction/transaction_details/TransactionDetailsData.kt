package com.bsel.remitngo.data.model.transaction.transaction_details


import com.google.gson.annotations.SerializedName

data class TransactionDetailsData(
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BenAmount")
    val benAmount: Double?,
    @SerializedName("BenName")
    val benName: String?,
    @SerializedName("CurrencyFromAmount")
    val currencyFromAmount: String?,
    @SerializedName("CurrencyToAmount")
    val currencyToAmount: String?,
    @SerializedName("CustomerName")
    val customerName: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("LiveStatus")
    val liveStatus: String?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("PayID")
    val payID: Int?,
    @SerializedName("PaymentMode")
    val paymentMode: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("TotalAmount")
    val totalAmount: Double?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?,
    @SerializedName("TransferFees")
    val transferFees: Double?,
    @SerializedName("TransferStatus")
    val transferStatus: String?
)