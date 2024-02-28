package com.bsel.remitngo.data.model.profile.county


import com.google.gson.annotations.SerializedName

data class CountyData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)