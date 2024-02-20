package com.bsel.remitngo.data.model.reason


import com.google.gson.annotations.SerializedName

data class ReasonData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)