package com.bsel.remitngo.data.model.document.documentType


import com.google.gson.annotations.SerializedName

data class DocumentTypeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<DocumentTypeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)