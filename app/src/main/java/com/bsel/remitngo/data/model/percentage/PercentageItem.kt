package com.bsel.remitngo.data.model.percentage


import com.google.gson.annotations.SerializedName

data class PercentageItem(
    @SerializedName("countryId")
    val countryId: Int?,
    @SerializedName("customerId")
    val customerId: Int?
)