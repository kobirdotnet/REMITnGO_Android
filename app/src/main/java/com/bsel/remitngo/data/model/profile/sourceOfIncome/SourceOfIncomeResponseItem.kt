package com.bsel.remitngo.data.model.profile.sourceOfIncome


import com.google.gson.annotations.SerializedName

data class SourceOfIncomeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<SourceOfIncomeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)