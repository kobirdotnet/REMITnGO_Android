package com.bsel.remitngo.data.model.beneficiary


import com.google.gson.annotations.SerializedName

data class AddBeneficiaryItem(
    @SerializedName("active")
    val active: Boolean?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("beneOccupation")
    val beneOccupation: Int?,
    @SerializedName("bic")
    val bic: String?,
    @SerializedName("countryID")
    val countryID: Int?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("districtID")
    val districtID: Int?,
    @SerializedName("divisionID")
    val divisionID: Int?,
    @SerializedName("emailId")
    val emailId: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("iban")
    val iban: String?,
    @SerializedName("identityType")
    val identityType: Int?,
    @SerializedName("isOnlineCustomer")
    val isOnlineCustomer: Int?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("middlename")
    val middlename: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("otherOccupation")
    val otherOccupation: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("relationType")
    val relationType: Int?,
    @SerializedName("resonID")
    val resonID: Int?,
    @SerializedName("thanaID")
    val thanaID: Int?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?
)