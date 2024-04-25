package com.bsel.remitngo.data.model.bank.bank_account


import com.google.gson.annotations.SerializedName

data class GetBankItem(
    @SerializedName("benePersonId")
    val benePersonId: Int?,
    @SerializedName("accountType")
    val accountType: Int?,
    @SerializedName("walletId")
    val walletId: Int?,
    @SerializedName("bankId")
    val bankId: Int?
)