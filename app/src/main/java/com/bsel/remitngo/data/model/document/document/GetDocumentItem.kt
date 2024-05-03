package com.bsel.remitngo.data.model.document.document


import com.google.gson.annotations.SerializedName

data class GetDocumentItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("DocumentId")
    val documentId: Int?
)