package com.bsel.remitngo.data.model.division


import com.google.gson.annotations.SerializedName

data class DivisionResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<DivisionData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)