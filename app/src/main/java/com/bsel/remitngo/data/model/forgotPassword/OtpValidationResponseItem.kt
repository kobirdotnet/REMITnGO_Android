package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class OtpValidationResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Any?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)