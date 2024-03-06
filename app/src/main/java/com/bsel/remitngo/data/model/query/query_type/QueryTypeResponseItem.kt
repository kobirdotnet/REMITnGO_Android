package com.bsel.remitngo.data.model.query.query_type


import com.google.gson.annotations.SerializedName

data class QueryTypeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<QueryTypeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)