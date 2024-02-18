package com.bsel.remitngo.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginItem(
    @SerializedName("channel") val channel: String?,
    @SerializedName("deviceId") val deviceId: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("userId") val userId: String?
)