package com.bsel.remitngo.data.model.profile.postCode


import com.google.gson.annotations.SerializedName

data class PostCodeItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PostCode")
    val postCode: String?
)