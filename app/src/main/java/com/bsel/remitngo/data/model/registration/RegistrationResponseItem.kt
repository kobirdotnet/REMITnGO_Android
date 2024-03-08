package com.bsel.remitngo.data.model.registration

import com.google.gson.annotations.SerializedName

data class RegistrationResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Any?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)
