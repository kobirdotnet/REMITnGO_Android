package com.bsel.remitngo.data.model.district


import com.google.gson.annotations.SerializedName

data class DistrictResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<DistrictData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)