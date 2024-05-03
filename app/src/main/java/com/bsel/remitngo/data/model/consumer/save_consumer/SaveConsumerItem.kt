package com.bsel.remitngo.data.model.consumer.save_consumer


import com.google.gson.annotations.SerializedName

data class SaveConsumerItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("ConsumerId")
    val consumerId: String?
)