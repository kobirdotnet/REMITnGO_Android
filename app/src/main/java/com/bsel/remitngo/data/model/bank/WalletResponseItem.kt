package com.bsel.remitngo.data.model.bank

import com.google.gson.annotations.SerializedName

data class WalletResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<WalletData?>?,
    @SerializedName("Message")
    val message: String?
)
