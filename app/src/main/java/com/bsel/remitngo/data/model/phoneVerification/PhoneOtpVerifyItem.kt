package com.bsel.remitngo.data.model.phoneVerification


import com.google.gson.annotations.SerializedName

data class PhoneOtpVerifyItem(
    @SerializedName("isVerifiyEmail")
    val isVerifiyEmail: Boolean?,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("personId")
    val personId: Int?
)