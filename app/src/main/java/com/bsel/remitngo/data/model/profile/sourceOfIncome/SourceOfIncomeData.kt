package com.bsel.remitngo.data.model.profile.sourceOfIncome


import com.google.gson.annotations.SerializedName

data class SourceOfIncomeData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)