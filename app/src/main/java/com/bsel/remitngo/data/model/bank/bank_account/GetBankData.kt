package com.bsel.remitngo.data.model.bank.bank_account


import com.google.gson.annotations.SerializedName

data class GetBankData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("AccountName")
    val accountName: String?,
    @SerializedName("BankId")
    val bankId: Int?,
    @SerializedName("BranchId")
    val branchId: Int?,
    @SerializedName("BranchName")
    val branchName: String?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("WalletName")
    val walletName: String?,
    @SerializedName("WalletId")
    val walletId: Int?

)