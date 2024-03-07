package com.bsel.remitngo.data.model.beneficiary.beneficiary


import com.google.gson.annotations.SerializedName

data class GetBeneficiaryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<GetBeneficiaryData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)