package com.bsel.remitngo.data.model.registration

import com.google.gson.annotations.SerializedName

data class RegistrationItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("channel")
    val channel: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("dob")
    val dob: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("refCode")
    val refCode: String?,
    @SerializedName("isOnlineCustomer")
    val isOnlineCustomer: Int?,
    @SerializedName("rdoemail")
    val rdoemail: Boolean?,
    @SerializedName("rdophone")
    val rdophone: Boolean?,
    @SerializedName("rdopost")
    val rdopost: Boolean?,
    @SerializedName("rdosms")
    val rdosms: Boolean?,
    @SerializedName("CountryId")
    val countryId: Int?
)
