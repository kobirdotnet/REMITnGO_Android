package com.bsel.remitngo.data.model.transaction.transaction_details


import com.google.gson.annotations.SerializedName

data class TransactionDetailsItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?
)