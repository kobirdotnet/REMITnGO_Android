package com.bsel.remitngo.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("Active")
    val active: Boolean?,
    @SerializedName("Address")
    val address: String?,
    @SerializedName("AnnualNetIncomeId")
    val annualNetIncomeId: Int?,
    @SerializedName("BCNStatus")
    val bCNStatus: Int?,
    @SerializedName("BIC")
    val bIC: Any?,
    @SerializedName("CityName")
    val cityName: String?,
    @SerializedName("CleanStringName")
    val cleanStringName: Any?,
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
    @SerializedName("ExportFlag")
    val exportFlag: Boolean?,
    @SerializedName("FatherName")
    val fatherName: Any?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("FirstPrevPass")
    val firstPrevPass: Any?,
    @SerializedName("FiscalCode")
    val fiscalCode: Any?,
    @SerializedName("Gender")
    val gender: Int?,
    @SerializedName("HouseName")
    val houseName: String?,
    @SerializedName("HouseNo")
    val houseNo: String?,
    @SerializedName("IBAN")
    val iBAN: Any?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("intCol1")
    val intCol1: Int?,
    @SerializedName("intCol2")
    val intCol2: Int?,
    @SerializedName("intCol3")
    val intCol3: Int?,
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
    @SerializedName("IsMGEnable")
    val isMGEnable: Int?,
    @SerializedName("IsMobileOTPValidate")
    val isMobileOTPValidate: Boolean?,
    @SerializedName("IsOTPValidationRequired")
    val isOTPValidationRequired: Boolean?,
    @SerializedName("IsOnlineCustomer")
    val isOnlineCustomer: Int?,
    @SerializedName("IsReferral")
    val isReferral: Boolean?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("MaidenName")
    val maidenName: String?,
    @SerializedName("MarketingByEmail")
    val marketingByEmail: Boolean?,
    @SerializedName("MarketingByPhone")
    val marketingByPhone: Boolean?,
    @SerializedName("MarketingByPost")
    val marketingByPost: Boolean?,
    @SerializedName("MarketingBySms")
    val marketingBySms: Boolean?,
    @SerializedName("MiddleName")
    val middleName: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("MobileVerified")
    val mobileVerified: Boolean?,
    @SerializedName("MotherName")
    val motherName: Any?,
    @SerializedName("NationalId")
    val nationalId: Any?,
    @SerializedName("Nationality")
    val nationality: Int?,
    @SerializedName("NonSanitizedMobileNumber")
    val nonSanitizedMobileNumber: Any?,
    @SerializedName("numCol1")
    val numCol1: Int?,
    @SerializedName("numCol2")
    val numCol2: Int?,
    @SerializedName("OccupationCode")
    val occupationCode: Int?,
    @SerializedName("OccupationTypeId")
    val occupationTypeId: Int?,
    @SerializedName("Password")
    val password: Any?,
    @SerializedName("PerAddress")
    val perAddress: Any?,
    @SerializedName("PerCountryId")
    val perCountryId: Int?,
    @SerializedName("PerDistrictId")
    val perDistrictId: Int?,
    @SerializedName("PerDivisionId")
    val perDivisionId: Int?,
    @SerializedName("PerPostCode")
    val perPostCode: Any?,
    @SerializedName("PerThanaId")
    val perThanaId: Int?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("PersonType")
    val personType: Int?,
    @SerializedName("Phone")
    val phone: Any?,
    @SerializedName("PostCode")
    val postCode: String?,
    @SerializedName("ReasonId")
    val reasonId: Int?,
    @SerializedName("RefCM")
    val refCM: Any?,
    @SerializedName("RegisterBy")
    val registerBy: Int?,
    @SerializedName("RegistrationDate")
    val registrationDate: String?,
    @SerializedName("RemitterPlaceOfBirth")
    val remitterPlaceOfBirth: Any?,
    @SerializedName("result")
    val result: Any?,
    @SerializedName("SecondLastName")
    val secondLastName: Any?,
    @SerializedName("SecondPrevPass")
    val secondPrevPass: Any?,
    @SerializedName("SenderAddress")
    val senderAddress: Any?,
    @SerializedName("SenderCity")
    val senderCity: Any?,
    @SerializedName("SenderState")
    val senderState: Any?,
    @SerializedName("SenderZipCode")
    val senderZipCode: Any?,
    @SerializedName("SourceOfFund")
    val sourceOfFund: Int?,
    @SerializedName("SourceOfIncomeId")
    val sourceOfIncomeId: Int?,
    @SerializedName("SpouseName")
    val spouseName: Any?,
    @SerializedName("strCol1")
    val strCol1: Any?,
    @SerializedName("strCol2")
    val strCol2: Any?,
    @SerializedName("strCol3")
    val strCol3: Any?,
    @SerializedName("strCol4")
    val strCol4: Any?,
    @SerializedName("strCol5")
    val strCol5: Any?,
    @SerializedName("ThanaId")
    val thanaId: Int?,
    @SerializedName("ThirdPrevPass")
    val thirdPrevPass: Any?,
    @SerializedName("Title")
    val title: Int?,
    @SerializedName("UpdateDate")
    val updateDate: String?,
    @SerializedName("UpdatedBy")
    val updatedBy: Int?,
    @SerializedName("VerificationCode")
    val verificationCode: String?
)