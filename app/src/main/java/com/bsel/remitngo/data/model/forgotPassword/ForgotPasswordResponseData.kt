package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponseData(
    @SerializedName("Message")
    val message: String?,
    @SerializedName("PersonId")
    val personId: Int?
)