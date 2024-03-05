package com.bsel.remitngo.data.model.cancel_request.cancel_reason


import com.google.gson.annotations.SerializedName

data class CancelReasonData(
    @SerializedName("Active")
    val active: Boolean?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("IsDeleted")
    val isDeleted: Boolean?,
    @SerializedName("IsOnline")
    val isOnline: Boolean?,
    @SerializedName("MGValue")
    val mGValue: String?,
    @SerializedName("MoneyTransferCancelReasonType")
    val moneyTransferCancelReasonType: Int?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("NameForOnline")
    val nameForOnline: String?,
    @SerializedName("UpdatedBy")
    val updatedBy: Int?,
    @SerializedName("UpdatedDate")
    val updatedDate: String?
)