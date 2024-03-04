package com.bsel.remitngo.data.model.document.uploadDocument


import com.google.gson.annotations.SerializedName
import java.io.File

data class UploadDocumentItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("file")
    val file: File?,
    @SerializedName("docId")
    val docId: Int?,
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("typeId")
    val typeId: Int?,
    @SerializedName("proofNo")
    val proofNo: Int?,
    @SerializedName("issueBy")
    val issueBy: String?,
    @SerializedName("issueDate")
    val issueDate: String?,
    @SerializedName("expireDate")
    val expireDate: String?,
    @SerializedName("updateDate")
    val updateDate: String?
)