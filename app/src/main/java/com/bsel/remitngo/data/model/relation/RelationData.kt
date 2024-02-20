package com.bsel.remitngo.data.model.relation


import com.google.gson.annotations.SerializedName

data class RelationData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)