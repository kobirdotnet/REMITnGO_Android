package com.bsel.remitngo.data.model.district


import com.google.gson.annotations.SerializedName

data class DistrictData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)