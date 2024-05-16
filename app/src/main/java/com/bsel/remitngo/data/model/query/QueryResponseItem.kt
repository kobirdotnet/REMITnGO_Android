package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `queryData`: List<QueryData?>?,
    @SerializedName("Message")
    val message: String?
)