package com.bsel.remitngo.data.model.document.docForTransaction


import com.google.gson.annotations.SerializedName

data class RequireDocumentData(
    @SerializedName("CategoryId")
    val categoryId: Int?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("HitId")
    val hitId: Int?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Status")
    val status: String?
)