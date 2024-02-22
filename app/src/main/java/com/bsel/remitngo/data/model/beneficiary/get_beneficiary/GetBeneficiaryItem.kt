package com.bsel.remitngo.data.model.beneficiary.get_beneficiary


import com.google.gson.annotations.SerializedName

data class GetBeneficiaryItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("orderType")
    val orderType: Int?,
    @SerializedName("countryId")
    val countryId: Int?
)