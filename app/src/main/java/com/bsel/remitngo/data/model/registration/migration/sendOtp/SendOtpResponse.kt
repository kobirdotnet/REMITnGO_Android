package com.bsel.remitngo.data.model.registration.migration.sendOtp


import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?
)