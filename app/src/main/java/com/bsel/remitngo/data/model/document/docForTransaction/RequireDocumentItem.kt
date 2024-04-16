package com.bsel.remitngo.data.model.document.docForTransaction


import com.google.gson.annotations.SerializedName

data class RequireDocumentItem(
    @SerializedName("agentId")
    val agentId: Int?,
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("beneficiaryId")
    val beneficiaryId: Int?,
    @SerializedName("customerId")
    val customerId: Int?,
    @SerializedName("entryDate")
    val entryDate: String?,
    @SerializedName("purposeOfTransferId")
    val purposeOfTransferId: Int?,
    @SerializedName("transactionType")
    val transactionType: Int?
)