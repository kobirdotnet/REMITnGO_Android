package com.bsel.remitngo.data.model.profile.occupationType


import com.google.gson.annotations.SerializedName

data class OccupationTypeItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("dropdownId")
    val dropdownId: Int?,
    @SerializedName("param1")
    val param1: Int?,
    @SerializedName("param2")
    val param2: Int?
)