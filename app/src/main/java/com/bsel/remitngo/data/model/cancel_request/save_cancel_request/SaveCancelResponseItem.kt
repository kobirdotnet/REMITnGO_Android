package com.bsel.remitngo.data.model.cancel_request.save_cancel_request


import com.google.gson.annotations.SerializedName

data class SaveCancelResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)