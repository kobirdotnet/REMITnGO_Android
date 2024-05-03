package com.bsel.remitngo.data.model.query.query_message


import com.google.gson.annotations.SerializedName

data class QueryMessageItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("ComplainId")
    val complainId: Int?
)