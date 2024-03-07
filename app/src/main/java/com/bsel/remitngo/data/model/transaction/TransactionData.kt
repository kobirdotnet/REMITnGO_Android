package com.bsel.remitngo.data.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BenAmount")
    val benAmount: Double?,
    @SerializedName("BenName")
    val benName: String?,
    @SerializedName("OrderStatus")
    val orderStatus: String?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("PaymentStatus")
    val paymentStatus: String?,
    @SerializedName("PaymentType")
    val paymentType: String?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?
)