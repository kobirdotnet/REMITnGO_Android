package com.bsel.remitngo.data.model.profile.county


import com.google.gson.annotations.SerializedName

data class CountyResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<CountyData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)