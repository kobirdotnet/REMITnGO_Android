package com.bsel.remitngo.data.model.document.getDocument


import com.google.gson.annotations.SerializedName

data class GetDocumentItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)