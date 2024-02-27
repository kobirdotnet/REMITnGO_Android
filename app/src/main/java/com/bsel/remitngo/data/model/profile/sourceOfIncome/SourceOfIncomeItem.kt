package com.bsel.remitngo.data.model.profile.sourceOfIncome


import com.google.gson.annotations.SerializedName

data class SourceOfIncomeItem(
    @SerializedName("deviceId")
    val deviceId: String?
)