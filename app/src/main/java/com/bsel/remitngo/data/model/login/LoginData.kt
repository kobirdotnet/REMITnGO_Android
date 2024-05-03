package com.bsel.remitngo.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("AccessToken")
    val accessToken: String?,
    @SerializedName("AtExpire")
    val atExpire: String?,
    @SerializedName("RefreshToken")
    val refreshToken: String?,
    @SerializedName("RtExpire")
    val rtExpire: String?
)