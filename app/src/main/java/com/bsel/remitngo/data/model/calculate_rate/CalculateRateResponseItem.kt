package com.bsel.remitngo.data.model.calculate_rate


import com.google.gson.annotations.SerializedName

data class CalculateRateResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<CalculateRateData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)