package com.bsel.remitngo.data.model.bankTransactionMessage


import com.google.gson.annotations.SerializedName

data class BankTransactionMessageData(
    @SerializedName("AccountName")
    val accountName: String?,
    @SerializedName("AccountNumber")
    val accountNumber: String?,
    @SerializedName("IBAN")
    val iBAN: String?,
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("SortCode")
    val sortCode: String?
)