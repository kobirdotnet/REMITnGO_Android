package com.bsel.remitngo.data.model.document.uploadDocument.documentType


import com.google.gson.annotations.SerializedName

data class DocumentTypeData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)