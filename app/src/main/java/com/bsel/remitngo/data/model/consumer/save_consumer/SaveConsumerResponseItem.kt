package com.bsel.remitngo.data.model.consumer.save_consumer


import com.google.gson.annotations.SerializedName

data class SaveConsumerResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)