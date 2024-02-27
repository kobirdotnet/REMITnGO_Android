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
    val bIC: String?,
    @SerializedName("CleanStringName")
    val cleanStringName: String?,
    @SerializedName("CountryId")
    val countryId: Int?,
    @SerializedName("DateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("DistrictId")
    val districtId: Int?,
    @SerializedName("DivisionId")
    val divisionId: Int?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("EmailVerified")
    val emailVerified: Boolean?,
    @SerializedName("ExpireDate")
    val expireDate: String?,
    @SerializedName("ExportFlag")
    val exportFlag: Boolean?,
    @SerializedName("FatherName")
    val fatherName: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("FirstPrevPass")
    val firstPrevPass: String?,
    @SerializedName("FiscalCode")
    val fiscalCode: String?,
    @SerializedName("Gender")
    val gender: Int?,
    @SerializedName("HouseName")
    val houseName: String?,
    @SerializedName("HouseNo")
    val houseNo: String?,
    @SerializedName("IBAN")
    val iBAN: String?,
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
    val motherName: String?,
    @SerializedName("NationalId")
    val nationalId: String?,
    @SerializedName("Nationality")
    val nationality: Int?,
    @SerializedName("NonSanitizedMobileNumber")
    val nonSanitizedMobileNumber: String?,
    @SerializedName("numCol1")
    val numCol1: Double?,
    @SerializedName("numCol2")
    val numCol2: Double?,
    @SerializedName("OccupationCode")
    val occupationCode: Int?,
    @SerializedName("OccupationTypeId")
    val occupationTypeId: Int?,
    @SerializedName("Password")
    val password: String?,
    @SerializedName("PerAddress")
    val perAddress: String?,
    @SerializedName("PerCountryId")
    val perCountryId: Int?,
    @SerializedName("PerDistrictId")
    val perDistrictId: Int?,
    @SerializedName("PerDivisionId")
    val perDivisionId: Int?,
    @SerializedName("PerPostCode")
    val perPostCode: String?,
    @SerializedName("PerThanaId")
    val perThanaId: Int?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("PersonType")
    val personType: Int?,
    @SerializedName("Phone")
    val phone: String?,
    @SerializedName("PostCode")
    val postCode: String?,
    @SerializedName("ReasonId")
    val reasonId: Int?,
    @SerializedName("RefCM")
    val refCM: String?,
    @SerializedName("RegisterBy")
    val registerBy: Int?,
    @SerializedName("RegistrationDate")
    val registrationDate: String?,
    @SerializedName("RemitterPlaceOfBirth")
    val remitterPlaceOfBirth: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("SecondLastName")
    val secondLastName: String?,
    @SerializedName("SecondPrevPass")
    val secondPrevPass: String?,
    @SerializedName("SenderAddress")
    val senderAddress: String?,
    @SerializedName("SenderCity")
    val senderCity: String?,
    @SerializedName("SenderState")
    val senderState: String?,
    @SerializedName("SenderZipCode")
    val senderZipCode: String?,
    @SerializedName("SourceOfFund")
    val sourceOfFund: Int?,
    @SerializedName("SourceOfIncomeId")
    val sourceOfIncomeId: Int?,
    @SerializedName("SpouseName")
    val spouseName: String?,
    @SerializedName("strCol1")
    val strCol1: String?,
    @SerializedName("strCol2")
    val strCol2: String?,
    @SerializedName("strCol3")
    val strCol3: String?,
    @SerializedName("strCol4")
    val strCol4: String?,
    @SerializedName("strCol5")
    val strCol5: String?,
    @SerializedName("ThanaId")
    val thanaId: Int?,
    @SerializedName("ThirdPrevPass")
    val thirdPrevPass: String?,
    @SerializedName("Title")
    val title: Int?,
    @SerializedName("UpdateDate")
    val updateDate: String?,
    @SerializedName("UpdatedBy")
    val updatedBy: Int?,
    @SerializedName("VerificationCode")
    val verificationCode: String?
)