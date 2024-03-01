package com.bsel.remitngo.data.model.document.getDocument


import com.google.gson.annotations.SerializedName

data class GetDocumentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<GetDocumentData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)