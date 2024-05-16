package com.bsel.remitngo.data.model.query.query_message


import com.google.gson.annotations.SerializedName

data class QueryMessageData(
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
    @SerializedName("ComplainUpdateByUser")
    val complainUpdateByUser: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("TransactionCode")
    val transactionCode: String?,
    @SerializedName("UpdateBy")
    val updateBy: Int?,
    @SerializedName("UpdateByUserType")
    val updateByUserType: Int?,
    @SerializedName("UpdateDate")
    val updateDate: String?
)