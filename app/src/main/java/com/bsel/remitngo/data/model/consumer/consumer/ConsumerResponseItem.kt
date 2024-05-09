package com.bsel.remitngo.data.model.consumer.consumer


import com.google.gson.annotations.SerializedName

data class ConsumerResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val consumerData: ConsumerData?,
    @SerializedName("Message")
    val message: String?
)