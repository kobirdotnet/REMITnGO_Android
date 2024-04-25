package com.bsel.remitngo.data.model.beneficiary.beneficiary


import com.google.gson.annotations.SerializedName

data class GetBeneficiaryItem(
    @SerializedName("customerId")
    val customerId: Int?,
    @SerializedName("countryId")
    val countryId: Int?
)