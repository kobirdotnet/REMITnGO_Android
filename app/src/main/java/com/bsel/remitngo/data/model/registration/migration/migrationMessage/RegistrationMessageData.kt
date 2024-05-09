package com.bsel.remitngo.data.model.registration.migration.migrationMessage


import com.google.gson.annotations.SerializedName

data class RegistrationMessageData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Message")
    val message: String?
)