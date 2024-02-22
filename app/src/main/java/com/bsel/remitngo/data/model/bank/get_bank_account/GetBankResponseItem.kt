package com.bsel.remitngo.data.model.bank.get_bank_account


import com.google.gson.annotations.SerializedName

data class GetBankResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<GetBankData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)