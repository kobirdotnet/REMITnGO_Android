package com.bsel.remitngo.data.model.transaction.transaction_details


import com.google.gson.annotations.SerializedName

data class TransactionDetailsData(
    @SerializedName("BeneAccountNo")
    val beneAccountNo: String?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("BeneBankId")
    val beneBankId: String?,
    @SerializedName("BeneBankName")
    val beneBankName: String?,
    @SerializedName("BeneBranchId")
    val beneBranchId: Int?,
    @SerializedName("BeneId")
    val beneId: String?,
    @SerializedName("BeneName")
    val beneName: String?,
    @SerializedName("BeneWalletId")
    val beneWalletId: Int?,
    @SerializedName("BeneWalletNo")
    val beneWalletNo: String?,
    @SerializedName("CustomerId")
    val customerId: Int?,
    @SerializedName("OrderStatus")
    val orderStatus: String?,
    @SerializedName("OrderType")
    val orderType: Int?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("PayingAgentId")
    val payingAgentId: Int?,
    @SerializedName("PaymentMode")
    val paymentMode: Int?,
    @SerializedName("PaymentStatus")
    val paymentStatus: String?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("PurposeOfTransferId")
    val purposeOfTransferId: Int?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SendAmount")
    val sendAmount: Double?,
    @SerializedName("SourceOfFundId")
    val sourceOfFundId: Int?,
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
    val transferFees: Double?
)