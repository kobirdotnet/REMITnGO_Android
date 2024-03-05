package com.bsel.remitngo.data.model.cancel_request.populate_cancel_request


import com.google.gson.annotations.SerializedName

data class PopulateCancelItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)