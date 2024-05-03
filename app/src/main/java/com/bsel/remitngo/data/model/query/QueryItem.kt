package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?
)