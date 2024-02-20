package com.bsel.remitngo.data.model.relation


import com.google.gson.annotations.SerializedName

data class RelationResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<RelationData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)