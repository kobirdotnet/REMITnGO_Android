package com.bsel.remitngo.data.model.registration.migration.setPassword


import com.google.gson.annotations.SerializedName

data class SetPasswordItemMigration(
    @SerializedName("channelId")
    val channelId: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("personId")
    val personId: Int?
)