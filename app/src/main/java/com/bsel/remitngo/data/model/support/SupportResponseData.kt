package com.bsel.remitngo.data.model.support


import com.google.gson.annotations.SerializedName

data class SupportResponseData(
    @SerializedName("Email")
    val email: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("MessagnerUrl")
    val messagnerUrl: String?,
    @SerializedName("MessangerMsg")
    val messangerMsg: String?,
    @SerializedName("PhoneNumber")
    val phoneNumber: String?,
    @SerializedName("WhatsAppMsg")
    val whatsAppMsg: String?,
    @SerializedName("WhatsappUrl")
    val whatsappUrl: String?
)