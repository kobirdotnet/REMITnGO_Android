package com.bsel.remitngo.data.model.cancel_request.populate_cancel_request


import com.google.gson.annotations.SerializedName

data class PopulateCancelData(
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BeneAccountName")
    val beneAccountName: String?,
    @SerializedName("BeneAccountNo")
    val beneAccountNo: String?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("BeneBankId")
    val beneBankId: Int?,
    @SerializedName("BeneBranchId")
    val beneBranchId: Int?,
    @SerializedName("BeneId")
    val beneId: Int?,
    @SerializedName("BeneMobile")
    val beneMobile: String?,
    @SerializedName("BeneWalletId")
    val beneWalletId: Int?,
    @SerializedName("BeneWalletNo")
    val beneWalletNo: String?,
    @SerializedName("BeneficiaryName")
    val beneficiaryName: String?,
    @SerializedName("CustomerId")
    val customerId: Int?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("OrderStatus")
    val orderStatus: Int?,
    @SerializedName("OrderStatusName")
    val orderStatusName: String?,
    @SerializedName("OrderTypeName")
    val orderTypeName: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("TransactionDateTime12hr")
    val transactionDateTime12hr: String?,
    @SerializedName("TransactionStatus")
    val transactionStatus: String?
)