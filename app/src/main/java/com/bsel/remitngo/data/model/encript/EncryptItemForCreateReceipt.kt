package com.bsel.remitngo.data.model.encript


import com.google.gson.annotations.SerializedName

data class EncryptItemForCreateReceipt(
    @SerializedName("key")
    val key: String?,
    @SerializedName("plainText")
    val plainText: String?
)