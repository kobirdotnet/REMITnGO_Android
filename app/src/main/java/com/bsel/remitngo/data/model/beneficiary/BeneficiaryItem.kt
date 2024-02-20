package com.bsel.remitngo.data.model.beneficiary


import com.google.gson.annotations.SerializedName

data class BeneficiaryItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("emailId")
    val emailId: String?,
    @SerializedName("countryID")
    val countryID: Int?,
    @SerializedName("divisionID")
    val divisionID: Int?,
    @SerializedName("districtID")
    val districtID: Int?,
    @SerializedName("thanaID")
    val thanaID: Int?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("isOnlineCustomer")
    val isOnlineCustomer: Int?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?,
    @SerializedName("relationType")
    val relationType: Int?,
    @SerializedName("resonID")
    val resonID: Int?,
    @SerializedName("iban")
    val iban: String?,
    @SerializedName("bic")
    val bic: String?,
    @SerializedName("identityType")
    val identityType: Int?,
    @SerializedName("beneOccupation")
    val beneOccupation: Int?,
    @SerializedName("otherOccupation")
    val otherOccupation: String?,
)