package com.bsel.remitngo.data.model.beneficiary.beneficiary


import com.google.gson.annotations.SerializedName

data class GetBeneficiaryData(
    @SerializedName("Address")
    val address: String?,
    @SerializedName("AlphaCode")
    val alphaCode: String?,
    @SerializedName("BIC")
    val bIC: String?,
    @SerializedName("BeneOccupation")
    val beneOccupation: Int?,
    @SerializedName("BeneOccupationOther")
    val beneOccupationOther: String?,
    @SerializedName("BeneficiaryId")
    val beneficiaryId: Int?,
    @SerializedName("CityName")
    val cityName: String?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("CountryName")
    val countryName: String?,
    @SerializedName("CustId")
    val custId: Int?,
    @SerializedName("DistrictId")
    val districtId: Int?,
    @SerializedName("DistrictName")
    val districtName: String?,
    @SerializedName("DivisionId")
    val divisionId: Int?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("Gender")
    val gender: Int?,
    @SerializedName("IBAN")
    val iBAN: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("IdentityType")
    val identityType: Int?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("MiddleName")
    val middleName: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Ordering")
    val ordering: Int?,
    @SerializedName("ReasonId")
    val reasonId: String?,
    @SerializedName("ReasonName")
    val reasonName: String?,
    @SerializedName("Relation")
    val relation: String?,
    @SerializedName("ThanaId")
    val thanaId: Int?,
    @SerializedName("ThanaName")
    val thanaName: String?
)