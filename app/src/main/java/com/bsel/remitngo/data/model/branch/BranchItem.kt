package com.bsel.remitngo.data.model.branch


import com.google.gson.annotations.SerializedName

data class BranchItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("bankId")
    val bankId: Int?,
    @SerializedName("toCountryId")
    val toCountryId: Int?,
    @SerializedName("divisionId")
    val divisionId: Int?,
    @SerializedName("districtId")
    val districtId: Int?
)