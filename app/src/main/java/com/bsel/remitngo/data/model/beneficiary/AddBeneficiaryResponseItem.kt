package com.bsel.remitngo.data.model.beneficiary


import com.google.gson.annotations.SerializedName

data class AddBeneficiaryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)