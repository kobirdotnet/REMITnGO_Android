package com.bsel.remitngo.data.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("BeneAccountNo")
    val beneAccountNo: String?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("BeneBankId")
    val beneBankId: Int?,
    @SerializedName("BeneBankName")
    val beneBankName: String?,
    @SerializedName("BeneName")
    val beneName: String?,
    @SerializedName("BeneWalletId")
    val beneWalletId: Int?,
    @SerializedName("BeneWalletNo")
    val beneWalletNo: String?,
    @SerializedName("CustomerId")
    val customerId: Int?,
    @SerializedName("EmpTransactionForm")
    val empTransactionForm: String?,
    @SerializedName("OrderStatus")
    val orderStatus: Int?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("PaymentMode")
    val paymentMode: Int?,
    @SerializedName("PaymentStatus")
    val paymentStatus: String?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SendAmount")
    val sendAmount: Double?,
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
    @SerializedName("WalletName")
    val walletName: String?
)