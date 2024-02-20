package com.bsel.remitngo.data.model.gender


import com.google.gson.annotations.SerializedName

data class GenderData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)