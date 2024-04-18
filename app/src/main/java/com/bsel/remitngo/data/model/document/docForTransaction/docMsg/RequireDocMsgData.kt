package com.bsel.remitngo.data.model.document.docForTransaction.docMsg


import com.google.gson.annotations.SerializedName

data class RequireDocMsgData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)