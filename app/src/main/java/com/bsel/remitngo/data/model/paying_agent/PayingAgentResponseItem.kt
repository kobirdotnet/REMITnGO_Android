package com.bsel.remitngo.data.model.paying_agent


import com.google.gson.annotations.SerializedName

data class PayingAgentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<PayingAgentData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)