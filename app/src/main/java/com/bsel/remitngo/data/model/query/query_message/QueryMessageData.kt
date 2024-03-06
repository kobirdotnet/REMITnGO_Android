package com.bsel.remitngo.data.model.query.query_message


import com.google.gson.annotations.SerializedName

data class QueryMessageData(
    @SerializedName("Table")
    val queryMessageTable: List<QueryMessageTable?>?
)