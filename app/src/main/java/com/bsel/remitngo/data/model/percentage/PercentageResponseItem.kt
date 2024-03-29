package com.bsel.remitngo.data.model.percentage


import com.google.gson.annotations.SerializedName

data class PercentageResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<PercentageData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)