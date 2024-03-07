package com.bsel.remitngo.data.model.cancel_request.cancel_request


import com.google.gson.annotations.SerializedName

data class GetCancelRequestItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)