package com.bsel.remitngo.data.model.registration.migration.setPassword


import com.google.gson.annotations.SerializedName

data class SetPasswordResponseItemMigration(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Any?,
    @SerializedName("Message")
    val message: String?
)