package com.bsel.remitngo.data.model.login


import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("CMCode")
    val cmCode: String?,
    @SerializedName("DateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("Email")
    val email: String?,
    @SerializedName("ExpireDate")
    val expireDate: String?,
    @SerializedName("FirstName")
    val firstName: String?,
    @SerializedName("FirstPrevPass")
    val firstPrevPass: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("IsActive")
    val isActive: Boolean?,
    @SerializedName("IsExpire")
    val isExpire: Boolean?,
    @SerializedName("IsFirstLogin")
    val isFirstLogin: Boolean?,
    @SerializedName("LastName")
    val lastName: String?,
    @SerializedName("MiddleName")
    val middleName: String?,
    @SerializedName("Mobile")
    val mobile: String?,
    @SerializedName("PersonId")
    val personId: Int?,
    @SerializedName("SecondPrevPass")
    val secondPrevPass: String?,
    @SerializedName("ThirdPrevPass")
    val thirdPrevPass: String?
)