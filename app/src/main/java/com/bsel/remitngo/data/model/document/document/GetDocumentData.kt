package com.bsel.remitngo.data.model.document.document


import com.google.gson.annotations.SerializedName

data class GetDocumentData(
    @SerializedName("Category")
    val category: String?,
    @SerializedName("CategoryId")
    val categoryId: Int?,
    @SerializedName("DocNo")
    val docNo: String?,
    @SerializedName("ExpireDate")
    val expireDate: String?,
    @SerializedName("FileName")
    val fileName: String?,
    @SerializedName("ID")
    val iD: Int?,
    @SerializedName("IssueBy")
    val issueBy: String?,
    @SerializedName("IssueDate")
    val issueDate: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("Status")
    val status: String?,
    @SerializedName("TypeId")
    val typeId: Int?
)