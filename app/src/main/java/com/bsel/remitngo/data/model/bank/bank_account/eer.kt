package com.bsel.remitngo.data.model.bank.bank_account


import com.google.gson.annotations.SerializedName

data class eer(
    @SerializedName("AccountName")
    val accountName: String?,
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("BankId")
    val bankId: Int?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("BranchId")
    val branchId: Int?,
    @SerializedName("BranchName")
    val branchName: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("WalletId")
    val walletId: Int?,
    @SerializedName("WalletName")
    val walletName: Any?
)