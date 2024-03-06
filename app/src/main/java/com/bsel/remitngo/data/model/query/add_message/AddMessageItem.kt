package com.bsel.remitngo.data.model.query.add_message


import com.google.gson.annotations.SerializedName

data class AddMessageItem(
    @SerializedName("checkTranNo")
    val checkTranNo: Boolean?,
    @SerializedName("complainId")
    val complainId: Int?,
    @SerializedName("complainMessage")
    val complainMessage: String?,
    @SerializedName("complainStatus")
    val complainStatus: Boolean?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("querySender")
    val querySender: Int?,
    @SerializedName("queryType")
    val queryType: Int?,
    @SerializedName("transactionNo")
    val transactionNo: String?,
    @SerializedName("userIPAddress")
    val userIPAddress: String?
)