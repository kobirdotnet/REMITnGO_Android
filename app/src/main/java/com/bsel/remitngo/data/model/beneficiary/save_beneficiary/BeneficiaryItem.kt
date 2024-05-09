package com.bsel.remitngo.data.model.beneficiary.save_beneficiary


import com.google.gson.annotations.SerializedName

data class BeneficiaryItem(
    @SerializedName("address")
    val address: String?,
    @SerializedName("beneficiaryId")
    val beneficiaryId: Int?,
    @SerializedName("beneficiaryName")
    val beneficiaryName: String?,
    @SerializedName("countryID")
    val countryID: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("districtID")
    val districtID: Int?,
    @SerializedName("divisionID")
    val divisionID: Int?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("operationType")
    val operationType: Int?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("relationType")
    val relationType: Int?,
    @SerializedName("thanaID")
    val thanaID: Int?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?
)