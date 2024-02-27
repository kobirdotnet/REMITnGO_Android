package com.bsel.remitngo.data.model.profile.annualIncome


import com.google.gson.annotations.SerializedName

data class AnnualIncomeData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)