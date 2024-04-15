package com.bsel.remitngo.data.model.phoneVerification


import com.google.gson.annotations.SerializedName

data class PhoneVerifyItem(
    @SerializedName("isVerifiyEmail")
    val isVerifiyEmail: Boolean?,
    @SerializedName("phoneOrEmail")
    val phoneOrEmail: String?
)