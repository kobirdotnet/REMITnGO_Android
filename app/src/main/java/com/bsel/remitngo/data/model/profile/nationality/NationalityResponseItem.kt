package com.bsel.remitngo.data.model.profile.nationality


import com.google.gson.annotations.SerializedName

data class NationalityResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<NationalityData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)