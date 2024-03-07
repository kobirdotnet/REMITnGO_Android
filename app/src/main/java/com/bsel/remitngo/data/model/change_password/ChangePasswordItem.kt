package com.bsel.remitngo.data.model.change_password


import com.google.gson.annotations.SerializedName

data class ChangePasswordItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("oldPassword")
    val oldPassword: String?,
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("confirmNewPassword")
    val confirmNewPassword: String?
)