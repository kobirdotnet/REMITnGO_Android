package com.bsel.remitngo.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?
)