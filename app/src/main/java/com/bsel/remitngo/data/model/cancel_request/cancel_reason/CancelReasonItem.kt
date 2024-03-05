package com.bsel.remitngo.data.model.cancel_request.cancel_reason


import com.google.gson.annotations.SerializedName

data class CancelReasonItem(
    @SerializedName("deviceId")
    val deviceId: String?
)