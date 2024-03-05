package com.bsel.remitngo.data.model.cancel_request.populate_cancel_request


import com.google.gson.annotations.SerializedName

data class PopulateCancelResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<PopulateCancelData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)