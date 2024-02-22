package com.bsel.remitngo.data.model.bank.save_bank_account


import com.google.gson.annotations.SerializedName

data class SaveBankResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)