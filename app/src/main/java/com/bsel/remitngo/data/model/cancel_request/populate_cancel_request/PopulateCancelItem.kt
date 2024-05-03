package com.bsel.remitngo.data.model.cancel_request.populate_cancel_request


import com.google.gson.annotations.SerializedName

data class PopulateCancelItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?
)