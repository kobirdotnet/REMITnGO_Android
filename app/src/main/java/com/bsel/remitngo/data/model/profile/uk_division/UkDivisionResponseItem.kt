package com.bsel.remitngo.data.model.profile.uk_division


import com.google.gson.annotations.SerializedName

data class UkDivisionResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<UkDivisionData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)