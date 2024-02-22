package com.bsel.remitngo.data.model.bank.save_bank_account


import com.google.gson.annotations.SerializedName

data class SaveBankItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("cusBankInfoID")
    val cusBankInfoID: Int?,
    @SerializedName("accountName")
    val accountName: String?,
    @SerializedName("bankID")
    val bankID: Int?,
    @SerializedName("branchID")
    val branchID: Int?,
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("isVersion113")
    val isVersion113: Int?,
    @SerializedName("accountType")
    val accountType: Int?,
    @SerializedName("active")
    val active: Boolean?
)