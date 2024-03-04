package com.bsel.remitngo.data.model.document.documentType


import com.google.gson.annotations.SerializedName

data class DocumentTypeItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)