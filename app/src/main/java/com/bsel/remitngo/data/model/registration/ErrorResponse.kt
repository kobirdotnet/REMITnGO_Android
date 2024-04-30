package com.bsel.remitngo.data.model.registration


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<ErrorResponseData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)