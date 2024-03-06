package com.bsel.remitngo.data.model.query.add_message


import com.google.gson.annotations.SerializedName

data class AddMessageResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)