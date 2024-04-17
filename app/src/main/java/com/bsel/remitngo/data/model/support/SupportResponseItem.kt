package com.bsel.remitngo.data.model.support


import com.google.gson.annotations.SerializedName

data class SupportResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<SupportResponseData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)