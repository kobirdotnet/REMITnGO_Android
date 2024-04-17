package com.bsel.remitngo.data.model.bankTransactionMessage


import com.google.gson.annotations.SerializedName

data class BankTransactionMessage(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<BankTransactionMessageData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)