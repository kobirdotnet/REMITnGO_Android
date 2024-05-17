package com.bsel.remitngo.data.model.transaction


import com.google.gson.annotations.SerializedName

data class TransactionResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<TransactionData?>?,
    @SerializedName("Message")
    val message: String?
)