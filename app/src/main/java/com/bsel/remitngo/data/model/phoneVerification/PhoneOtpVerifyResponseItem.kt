package com.bsel.remitngo.data.model.phoneVerification


import com.google.gson.annotations.SerializedName

data class PhoneOtpVerifyResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)