package com.bsel.remitngo.data.model.encript


import com.google.gson.annotations.SerializedName

data class EncryptResponseItemForCreateReceipt(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)