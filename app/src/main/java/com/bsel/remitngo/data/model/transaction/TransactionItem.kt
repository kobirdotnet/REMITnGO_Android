package com.bsel.remitngo.data.model.transaction


import com.google.gson.annotations.SerializedName

data class TransactionItem(
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("params1")
    val params1: Int?,
    @SerializedName("params2")
    val params2: Int?
)