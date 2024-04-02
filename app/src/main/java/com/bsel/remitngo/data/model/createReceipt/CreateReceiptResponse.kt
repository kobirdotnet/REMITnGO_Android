package com.bsel.remitngo.data.model.createReceipt


import com.google.gson.annotations.SerializedName

data class CreateReceiptResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)