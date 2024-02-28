package com.bsel.remitngo.data.model.profile.city


import com.google.gson.annotations.SerializedName

data class CityResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<CityData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)