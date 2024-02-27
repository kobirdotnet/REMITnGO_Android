package com.bsel.remitngo.data.model.profile.annualIncome


import com.google.gson.annotations.SerializedName

data class AnnualIncomeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<AnnualIncomeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)