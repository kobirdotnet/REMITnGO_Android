package com.bsel.remitngo.data.model.document.docForTransaction


import com.google.gson.annotations.SerializedName

data class RequireDocumentData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Sl")
    val sl: Int?
)