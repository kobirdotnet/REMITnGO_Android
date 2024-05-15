package com.bsel.remitngo.data.model.query.query_message


import com.google.gson.annotations.SerializedName

data class QueryMessageTable(
    @SerializedName("CompStatus")
    val compStatus: Boolean?,
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
    @SerializedName("ComplainUpdateByUserType")
    val complainUpdateByUserType: String?,
    @SerializedName("DateStringForApp")
    val dateStringForApp: Any?,
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