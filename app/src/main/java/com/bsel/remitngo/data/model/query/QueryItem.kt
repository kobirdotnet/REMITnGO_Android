package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)