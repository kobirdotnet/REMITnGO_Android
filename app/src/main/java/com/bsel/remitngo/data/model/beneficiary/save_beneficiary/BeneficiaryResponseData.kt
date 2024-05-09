package com.bsel.remitngo.data.model.beneficiary.save_beneficiary


import com.google.gson.annotations.SerializedName

data class BeneficiaryResponseData(
    @SerializedName("BenePersonId")
    val benePersonId: Int?,
    @SerializedName("BeneficiaryId")
    val beneficiaryId: Int?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Mobile")
    val mobile: String?
)