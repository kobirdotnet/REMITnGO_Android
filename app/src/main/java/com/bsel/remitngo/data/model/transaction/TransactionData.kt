package com.bsel.remitngo.data.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("Amount")
    val amount: Any?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BenAmount")
    val benAmount: Any?,
    @SerializedName("BenName")
    val benName: String?,
    @SerializedName("BeneBankAccountName")
    val beneBankAccountName: Any?,
    @SerializedName("BeneBankId")
    val beneBankId: Any?,
    @SerializedName("BeneBranchId")
    val beneBranchId: Int?,
    @SerializedName("BeneId")
    val beneId: Any?,
    @SerializedName("OrderStatus")
    val orderStatus: String?,
    @SerializedName("OrderTypeId")
    val orderTypeId: Any?,
    @SerializedName("OrderTypeName")
    val orderTypeName: Any?,
    @SerializedName("PayingAgentId")
    val payingAgentId: Any?,
    @SerializedName("PaymentMode")
    val paymentMode: String?,
    @SerializedName("PaymentStatus")
    val paymentStatus: String?,
    @SerializedName("PaymentType")
    val paymentType: String?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("PurposeOfTransferId")
    val purposeOfTransferId: Int?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SendAmount")
    val sendAmount: Any?,
    @SerializedName("SourceOfFundId")
    val sourceOfFundId: Int?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDate")
    val transactionDate: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?,
    @SerializedName("TransferFees")
    val transferFees: Any?
)