package com.bsel.remitngo.data.model.cancel_request.save_cancel_request


import com.google.gson.annotations.SerializedName

data class SaveCancelRequestItem(
    @SerializedName("cancelReasonId")
    val cancelReasonId: Int?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("remarks")
    val remarks: String?,
    @SerializedName("transactionCode")
    val transactionCode: String?
)