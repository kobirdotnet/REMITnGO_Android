package com.bsel.remitngo.data.model.consumer.save_consumer


import com.google.gson.annotations.SerializedName

data class SaveConsumerItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: String?
)