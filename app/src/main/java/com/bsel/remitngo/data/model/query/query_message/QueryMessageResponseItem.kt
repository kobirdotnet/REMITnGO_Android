package com.bsel.remitngo.data.model.query.query_message


import com.google.gson.annotations.SerializedName

data class QueryMessageResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `queryMessageData`: List<QueryMessageData?>?,
    @SerializedName("Message")
    val message: String?
)