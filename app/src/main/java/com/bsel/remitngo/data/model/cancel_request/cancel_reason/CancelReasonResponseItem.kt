package com.bsel.remitngo.data.model.cancel_request.cancel_reason


import com.google.gson.annotations.SerializedName

data class CancelReasonResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<CancelReasonData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)