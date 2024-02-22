package com.bsel.remitngo.data.model.bank.get_bank_account


import com.google.gson.annotations.SerializedName

data class GetBankItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)