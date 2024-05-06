package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: ForgotPasswordResponseData?,
    @SerializedName("Message")
    val message: String?
)