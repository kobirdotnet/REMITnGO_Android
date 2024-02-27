package com.bsel.remitngo.data.model.profile.occupationType


import com.google.gson.annotations.SerializedName

data class OccupationTypeData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)