package com.bsel.remitngo.data.model.document.uploadDocument


import com.google.gson.annotations.SerializedName

data class UploadDocumentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?
)