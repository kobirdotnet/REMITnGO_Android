package com.bsel.remitngo.data.model.change_password


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)