package com.bsel.remitngo.data.model.marketing


import com.google.gson.annotations.SerializedName

data class MarketingResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)