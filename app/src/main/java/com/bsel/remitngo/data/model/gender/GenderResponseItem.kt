package com.bsel.remitngo.data.model.gender


import com.google.gson.annotations.SerializedName

data class GenderResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<GenderData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)