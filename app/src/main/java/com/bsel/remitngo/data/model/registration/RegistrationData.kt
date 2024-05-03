package com.bsel.remitngo.data.model.registration


import com.google.gson.annotations.SerializedName

data class RegistrationData(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("IsLogin")
    val isLogin: Boolean?,
    @SerializedName("IsMigrate")
    val isMigrate: Boolean?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("PersonId")
    val personId: Int?
)