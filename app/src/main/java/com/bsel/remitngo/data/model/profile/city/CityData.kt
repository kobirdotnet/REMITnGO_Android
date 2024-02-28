package com.bsel.remitngo.data.model.profile.city


import com.google.gson.annotations.SerializedName

data class CityData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)