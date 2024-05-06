package com.bsel.remitngo.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("Active")
    val active: Boolean?,
    @SerializedName("Address")
    val address: String?,
    @SerializedName("AnnualNetIncomeId")
    val annualNetIncomeId: Int?,
    @SerializedName("CityName")
    val cityName: String?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("CountryName")
    val countryName: String?,
    @SerializedName("DateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("DistrictId")
    val districtId: Int?,
    @SerializedName("DistrictName")
    val districtName: String?,
    @SerializedName("DivisionId")
    val divisionId: Int?,
    @SerializedName("DivisionName")
    val divisionName: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("EmailVerified")
    val emailVerified: Boolean?,
    @SerializedName("ExpireDate")
    val expireDate: String?,
    @SerializedName("FatherName")
    val fatherName: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("FirstPrevPass")
    val firstPrevPass: String?,
    @SerializedName("Gender")
    val gender: Int?,
    @SerializedName("HouseName")
    val houseName: String?,
    @SerializedName("HouseNo")
    val houseNo: String?,
    @SerializedName("IsActive")
    val isActive: Boolean?,
    @SerializedName("IsDeleted")
    val isDeleted: Boolean?,
    @SerializedName("IsExpire")
    val isExpire: Boolean?,
    @SerializedName("IsFirstLogin")
    val isFirstLogin: Int?,
    @SerializedName("IsFirstTranComp")
    val isFirstTranComp: Boolean?,
    @SerializedName("IsLocked")
    val isLocked: Boolean?,
    @SerializedName("IsMobileOTPValidate")
    val isMobileOTPValidate: Boolean?,
    @SerializedName("IsOTPValidationRequired")
    val isOTPValidationRequired: Boolean?,
    @SerializedName("IsOnlineCustomer")
    val isOnlineCustomer: Int?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("MobileVerified")
    val mobileVerified: Boolean?,
    @SerializedName("MotherName")
    val motherName: String?,
    @SerializedName("NationalId")
    val nationalId: String?,
    @SerializedName("Nationality")
    val nationality: Int?,
    @SerializedName("OccupationCode")
    val occupationCode: Int?,
    @SerializedName("OccupationTypeId")
    val occupationTypeId: Int?,
    @SerializedName("Password")
    val password: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("PersonType")
    val personType: Int?,
    @SerializedName("PostCode")
    val postCode: String?,
    @SerializedName("RefCM")
    val refCM: String?,
    @SerializedName("RegisterBy")
    val registerBy: Int?,
    @SerializedName("RegistrationDate")
    val registrationDate: String?,
    @SerializedName("RemitterPlaceOfBirth")
    val remitterPlaceOfBirth: String?,
    @SerializedName("SecondPrevPass")
    val secondPrevPass: String?,
    @SerializedName("SourceOfFund")
    val sourceOfFund: Int?,
    @SerializedName("SourceOfIncomeId")
    val sourceOfIncomeId: Int?,
    @SerializedName("ThanaId")
    val thanaId: Int?,
    @SerializedName("ThirdPrevPass")
    val thirdPrevPass: String?,
    @SerializedName("UpdateDate")
    val updateDate: String?,
    @SerializedName("UpdatedBy")
    val updatedBy: Int?
)