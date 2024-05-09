package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class OtpValidationItem(
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("personId")
    val personId: Int?
)