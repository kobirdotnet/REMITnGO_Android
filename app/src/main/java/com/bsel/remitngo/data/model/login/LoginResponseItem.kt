package com.bsel.remitngo.data.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<LoginData>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)