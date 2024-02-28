package com.bsel.remitngo.data.model.profile.postCode


import com.google.gson.annotations.SerializedName

data class PostCodeItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: String?
)