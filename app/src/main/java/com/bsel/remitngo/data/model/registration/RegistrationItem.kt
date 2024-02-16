package com.bsel.remitngo.data.model.registration

import com.google.gson.annotations.SerializedName

data class RegistrationItem(
    @SerializedName("dob") val dob: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("firstname") val firstname: String?,
    @SerializedName("isOnlineCustomer") val isOnlineCustomer: Int?,
    @SerializedName("lastname") val lastname: String?,
    @SerializedName("middlename") val middlename: String?,
    @SerializedName("mobile") val mobile: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("rdoemail") val rdoemail: Boolean?,
    @SerializedName("rdophone") val rdophone: Boolean?,
    @SerializedName("rdopost") val rdopost: Boolean?,
    @SerializedName("rdosms") val rdosms: Boolean?,
    @SerializedName("refCode") val refCode: String?
)
