package com.bsel.remitngo.data.model.beneficiary.save_beneficiary


import com.google.gson.annotations.SerializedName

data class BeneficiaryResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<BeneficiaryResponseData?>?,
    @SerializedName("Message")
    val message: String?
)