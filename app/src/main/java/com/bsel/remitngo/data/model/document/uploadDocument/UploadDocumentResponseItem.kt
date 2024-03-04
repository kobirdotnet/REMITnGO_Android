package com.bsel.remitngo.data.model.document.uploadDocument


import com.google.gson.annotations.SerializedName

data class UploadDocumentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)