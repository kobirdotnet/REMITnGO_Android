package com.bsel.remitngo.data.model.bank.bank_account


import com.google.gson.annotations.SerializedName

data class GetBankResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val getBankData: List<GetBankData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)