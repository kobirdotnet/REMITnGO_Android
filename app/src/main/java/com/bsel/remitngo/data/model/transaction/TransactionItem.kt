package com.bsel.remitngo.data.model.transaction


import com.google.gson.annotations.SerializedName

data class TransactionItem(
    @SerializedName("DeviceId")
    val deviceId: String?,
    @SerializedName("PersonId")
    val personId: Int?
)