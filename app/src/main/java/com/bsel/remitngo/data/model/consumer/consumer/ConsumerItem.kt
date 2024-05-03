package com.bsel.remitngo.data.model.consumer.consumer


import com.google.gson.annotations.SerializedName

data class ConsumerItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?
)