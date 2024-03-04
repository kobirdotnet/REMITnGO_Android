package com.bsel.remitngo.data.model.document.documentCategory


import com.google.gson.annotations.SerializedName

data class DocumentCategoryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<DocumentCategoryData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)