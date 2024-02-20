package com.bsel.remitngo.data.model.reason


import com.google.gson.annotations.SerializedName

data class ReasonResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<ReasonData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)