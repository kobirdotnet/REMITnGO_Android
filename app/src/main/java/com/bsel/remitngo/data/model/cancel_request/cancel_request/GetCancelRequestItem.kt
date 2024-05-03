package com.bsel.remitngo.data.model.cancel_request.cancel_request


import com.google.gson.annotations.SerializedName

data class GetCancelRequestItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?
)