package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryData(
    @SerializedName("Table")
    val queryTable: List<QueryTable?>?
)