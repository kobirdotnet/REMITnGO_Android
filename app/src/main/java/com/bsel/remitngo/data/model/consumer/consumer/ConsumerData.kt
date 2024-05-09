package com.bsel.remitngo.data.model.consumer.consumer


import com.google.gson.annotations.SerializedName

data class ConsumerData(
    @SerializedName("ConsumerId")
    val consumerId: String?
)