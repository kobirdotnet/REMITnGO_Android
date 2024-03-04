package com.bsel.remitngo.data.model.document.documentCategory


import com.google.gson.annotations.SerializedName

data class DocumentCategoryData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)