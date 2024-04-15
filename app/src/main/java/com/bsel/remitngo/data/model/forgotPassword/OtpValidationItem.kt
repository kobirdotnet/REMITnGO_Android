package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class OtpValidationItem(
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("OtpSendBy")
    val otpSendBy: String?,
    @SerializedName("otpType")
    val otpType: Int?,
    @SerializedName("personId")
    val personId: Int?
)