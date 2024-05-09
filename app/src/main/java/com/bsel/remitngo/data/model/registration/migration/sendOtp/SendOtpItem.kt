package com.bsel.remitngo.data.model.registration.migration.sendOtp


import com.google.gson.annotations.SerializedName

data class SendOtpItem(
    @SerializedName("channelId")
    val channelId: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("mobile")
    val mobile: String?
)