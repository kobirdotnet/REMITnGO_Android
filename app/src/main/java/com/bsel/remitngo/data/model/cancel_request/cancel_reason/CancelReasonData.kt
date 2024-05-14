package com.bsel.remitngo.data.model.cancel_request.cancel_reason


import com.google.gson.annotations.SerializedName

data class CancelReasonData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)