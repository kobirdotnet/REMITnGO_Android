package com.bsel.remitngo.data.model.cancel_request.cancel_request


import com.google.gson.annotations.SerializedName

data class GetCancelResponseData(
    @SerializedName("BeneficiaryAmount")
    val beneficiaryAmount: Int?,
    @SerializedName("CancelReason")
    val cancelReason: String?,
    @SerializedName("OrderStatusName")
    val orderStatusName: String?,
    @SerializedName("OrdertypeName")
    val ordertypeName: String?,
    @SerializedName("SendAmount")
    val sendAmount: Double?,
    @SerializedName("TransactionCode")
    val transactionCode: String?
)