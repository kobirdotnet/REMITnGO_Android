package com.bsel.remitngo.data.model.document.docForTransaction.docMsg


import com.google.gson.annotations.SerializedName

data class RequireDocMsg(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<RequireDocMsgData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)