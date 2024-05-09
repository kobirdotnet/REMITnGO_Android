package com.bsel.remitngo.data.model.registration.migration.migrationMessage


import com.bsel.remitngo.data.model.registration.migration.migrationMessage.RegistrationMessageData
import com.google.gson.annotations.SerializedName

data class RegistrationResponseMessage(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<RegistrationMessageData?>?,
    @SerializedName("Message")
    val message: String?
)