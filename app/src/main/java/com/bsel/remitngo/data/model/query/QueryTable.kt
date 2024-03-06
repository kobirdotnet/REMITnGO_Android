package com.bsel.remitngo.data.model.query


import com.google.gson.annotations.SerializedName

data class QueryTable(
    @SerializedName("ComplainDetailsId")
    val complainDetailsId: Int?,
    @SerializedName("ComplainID")
    val complainID: Int?,
    @SerializedName("ComplainStatus")
    val complainStatus: Boolean?,
    @SerializedName("ComplainStatusString")
    val complainStatusString: String?,
    @SerializedName("ComplainType")
    val complainType: Int?,
    @SerializedName("ComplainTypeString")
    val complainTypeString: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?
)