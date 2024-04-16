package com.bsel.remitngo.data.model.document.docForTransaction


import com.google.gson.annotations.SerializedName

data class RequireDocumentResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<RequireDocumentData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)