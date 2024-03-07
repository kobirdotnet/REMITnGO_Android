package com.bsel.remitngo.data.model.bank.bank_account


import com.google.gson.annotations.SerializedName

data class GetBankData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Type")
    val type: String?,
    @SerializedName("CountryName")
    val countryName: String?,
    @SerializedName("BankId")
    val bankId: Int?,
    @SerializedName("BanksName")
    val banksName: String?,
    @SerializedName("BranchId")
    val branchId: Int?,
    @SerializedName("BankName")
    val bankName: String?,
    @SerializedName("AccountNo")
    val accountNo: String?,
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("IsDirectCredit")
    val isDirectCredit: Boolean?,
    @SerializedName("BranchDivisionId")
    val branchDivisionId: Int?
)