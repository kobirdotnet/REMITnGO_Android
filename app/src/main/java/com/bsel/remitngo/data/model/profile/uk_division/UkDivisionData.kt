package com.bsel.remitngo.data.model.profile.uk_division


import com.google.gson.annotations.SerializedName

data class UkDivisionData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)