package com.bsel.remitngo.data.model.bank.save_bank_account


import com.google.gson.annotations.SerializedName

data class SaveBankResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val saveBankResponseData: List<SaveBankResponseData?>?,
    @SerializedName("Message")
    val message: String?
)