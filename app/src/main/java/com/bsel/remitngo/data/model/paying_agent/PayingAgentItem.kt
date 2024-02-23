package com.bsel.remitngo.data.model.paying_agent


import com.google.gson.annotations.SerializedName

data class PayingAgentItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("fromCountryId")
    val fromCountryId: Int?,
    @SerializedName("toCountryId")
    val toCountryId: Int?,
    @SerializedName("orderTypeId")
    val orderTypeId: Int?,
    @SerializedName("amount")
    val amount: Int?
)