package com.bsel.remitngo.data.model.division


import com.google.gson.annotations.SerializedName

data class DivisionData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)