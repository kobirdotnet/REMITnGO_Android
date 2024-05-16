package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryData(
    @SerializedName("ComplainDetailsId")
    val complainDetailsId: Int?,
    @SerializedName("ComplainId")
    val complainId: Int?,
    @SerializedName("ComplainStatus")
    val complainStatus: Boolean?,
    @SerializedName("ComplainStatusName")
    val complainStatusName: String?,
    @SerializedName("ComplainType")
    val complainType: Int?,
    @SerializedName("ComplainTypeName")
    val complainTypeName: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?
)