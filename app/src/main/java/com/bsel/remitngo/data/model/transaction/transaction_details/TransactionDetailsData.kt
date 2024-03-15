package com.bsel.remitngo.data.model.transaction.transaction_details


import com.google.gson.annotations.SerializedName

data class TransactionDetailsData(
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BenAmount")
    val benAmount: Int?,
    @SerializedName("BenName")
    val benName: String?,
    @SerializedName("BeneBankAccountName")
    val beneBankAccountName: String?,
    @SerializedName("BeneBankId")
    val beneBankId: String?,
    @SerializedName("BeneId")
    val beneId: String?,
    @SerializedName("OrderStatus")
    val orderStatus: String?,
    @SerializedName("OrderTypeId")
    val orderTypeId: String?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("PayingAgentId")
    val payingAgentId: String?,
    @SerializedName("PaymentStatus")
    val paymentStatus: String?,
    @SerializedName("PaymentType")
    val paymentType: String?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SendAmount")
    val sendAmount: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?,
    @SerializedName("TransferFees")
    val transferFees: String?
)