package com.bsel.remitngo.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<ProfileData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)