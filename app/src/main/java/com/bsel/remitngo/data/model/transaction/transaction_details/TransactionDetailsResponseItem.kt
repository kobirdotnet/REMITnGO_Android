package com.bsel.remitngo.data.model.transaction.transaction_details


import com.google.gson.annotations.SerializedName

data class TransactionDetailsResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<TransactionDetailsData?>?,
    @SerializedName("Message")
    val message: String?
)