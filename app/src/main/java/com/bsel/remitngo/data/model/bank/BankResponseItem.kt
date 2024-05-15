package com.bsel.remitngo.data.model.bank


import com.google.gson.annotations.SerializedName

data class BankResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<BankData?>?,
    @SerializedName("Message")
    val message: String?
)