package com.bsel.remitngo.data.model.profile.nationality


import com.google.gson.annotations.SerializedName

data class NationalityData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)