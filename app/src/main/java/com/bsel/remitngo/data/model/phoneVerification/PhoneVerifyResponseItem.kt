package com.bsel.remitngo.data.model.phoneVerification


import com.google.gson.annotations.SerializedName

data class PhoneVerifyResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Any?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)