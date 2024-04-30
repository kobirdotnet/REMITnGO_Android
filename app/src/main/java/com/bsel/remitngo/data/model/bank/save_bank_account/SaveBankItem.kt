package com.bsel.remitngo.data.model.bank.save_bank_account


import com.google.gson.annotations.SerializedName

data class SaveBankItem(
    @SerializedName("accountName")
    val accountName: String?,
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("accountType")
    val accountType: Int?,
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("bankID")
    val bankID: Int?,
    @SerializedName("benePersonId")
    val benePersonId: Int?,
    @SerializedName("branchID")
    val branchID: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?,
    @SerializedName("walletId")
    val walletId: Int?
)