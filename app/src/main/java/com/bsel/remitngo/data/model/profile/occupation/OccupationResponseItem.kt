package com.bsel.remitngo.data.model.profile.occupation


import com.google.gson.annotations.SerializedName

data class OccupationResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<OccupationData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)