package com.bsel.remitngo.data.model.profile.occupationType


import com.google.gson.annotations.SerializedName

data class OccupationTypeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<OccupationTypeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)