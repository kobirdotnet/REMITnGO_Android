package com.bsel.remitngo.data.model.query.add_query


import com.google.gson.annotations.SerializedName

data class AddQueryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)