package com.bsel.remitngo.data.model.bank.save_bank_account


import com.google.gson.annotations.SerializedName

data class SaveBankResponseData(
    @SerializedName("AccountName")
    val accountName: String?,
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("BankId")
    val bankId: Int?,
    @SerializedName("BenePersonId")
    val benePersonId: Int?,
    @SerializedName("BranchId")
    val branchId: Int?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("WalletId")
    val walletId: Int?
)