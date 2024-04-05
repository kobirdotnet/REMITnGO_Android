package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class ForgotPasswordItem(
    @SerializedName("isForgotByEmail")
    val isForgotByEmail: Boolean?,
    @SerializedName("phoneOrEmail")
    val phoneOrEmail: String?
)