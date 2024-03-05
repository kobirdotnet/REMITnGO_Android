package com.bsel.remitngo.data.model.cancel_request.get_cancel_request


import com.google.gson.annotations.SerializedName

data class GetCancelResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<GetCancelResponseData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)