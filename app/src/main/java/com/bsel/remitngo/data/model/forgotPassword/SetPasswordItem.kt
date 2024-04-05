package com.bsel.remitngo.data.model.forgotPassword


import com.google.gson.annotations.SerializedName

data class SetPasswordItem(
    @SerializedName("confirmNewPassword")
    val confirmNewPassword: String?,
    @SerializedName("newPassword")
    val newPassword: String?,
    @SerializedName("personId")
    val personId: Int?
)