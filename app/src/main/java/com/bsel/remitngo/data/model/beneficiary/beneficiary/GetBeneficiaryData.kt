package com.bsel.remitngo.data.model.beneficiary.beneficiary


import com.google.gson.annotations.SerializedName

data class GetBeneficiaryData(
    @SerializedName("BeneName")
    val beneName: String?,
    @SerializedName("BenePersonId")
    val benePersonId: Int?,
    @SerializedName("BeneficiaryId")
    val beneficiaryId: Int?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("CountryName")
    val countryName: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("FlagiconName")
    val flagiconName: String?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("HasTransactions")
    val hasTransactions: Boolean?
)