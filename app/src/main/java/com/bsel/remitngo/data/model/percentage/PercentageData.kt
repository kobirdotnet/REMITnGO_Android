package com.bsel.remitngo.data.model.percentage


import com.google.gson.annotations.SerializedName

data class PercentageData(
    @SerializedName("CampingMessage")
    val campingMessage: String?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("CountryName")
    val countryName: String?,
    @SerializedName("Url")
    val url: String?
)