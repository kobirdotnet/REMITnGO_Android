package com.bsel.remitngo.data.model.profile.occupation


import com.google.gson.annotations.SerializedName

data class OccupationData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)