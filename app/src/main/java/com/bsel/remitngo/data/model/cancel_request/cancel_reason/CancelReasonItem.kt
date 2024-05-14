package com.bsel.remitngo.data.model.cancel_request.cancel_reason


import com.google.gson.annotations.SerializedName

data class CancelReasonItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("dropdownId")
    val dropdownId: Int?,
    @SerializedName("param1")
    val param1: Int?,
    @SerializedName("param2")
    val param2: Int?
)