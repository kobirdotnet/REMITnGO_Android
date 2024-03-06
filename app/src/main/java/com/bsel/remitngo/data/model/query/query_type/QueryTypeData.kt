package com.bsel.remitngo.data.model.query.query_type


import com.google.gson.annotations.SerializedName

data class QueryTypeData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)