package com.bsel.remitngo.data.model.profile.updateProfile


import com.google.gson.annotations.SerializedName

data class UpdateProfileItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("updateType")
    val updateType: Int?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("nationality")
    val nationality: Int?,
    @SerializedName("occupationTypeId")
    val occupationTypeId: Int?,
    @SerializedName("occupationCode")
    val occupationCode: Int?,
    @SerializedName("postcode")
    val postcode: String?,
    @SerializedName("divisionId")
    val divisionId: Int?,
    @SerializedName("districtId")
    val districtId: Int?,
    @SerializedName("thanaId")
    val thanaId: Int?,
    @SerializedName("buildingno")
    val buildingno: String?,
    @SerializedName("housename")
    val housename: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("annualNetIncomeId")
    val annualNetIncomeId: Int?,
    @SerializedName("sourceOfIncomeId")
    val sourceOfIncomeId: Int?,
    @SerializedName("sourceOfFundId")
    val sourceOfFundId: Int?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?
)