package com.bsel.remitngo.data.model.percentage


import com.google.gson.annotations.SerializedName

data class PercentageItem(
    @SerializedName("MessageType")
    val messageType: Int?,
    @SerializedName("Parameter1")
    val parameter1: Int?,
    @SerializedName("Parameter2")
    val parameter2: Int?
)