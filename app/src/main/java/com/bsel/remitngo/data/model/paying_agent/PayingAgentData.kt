package com.bsel.remitngo.data.model.paying_agent


import com.google.gson.annotations.SerializedName

data class PayingAgentData(
    @SerializedName("BankId")
    val bankId: Int?,
    @SerializedName("WalletId")
    val walletId: Int?,
    @SerializedName("PayingAgentId")
    val payingAgentId: Int?,
    @SerializedName("Name")
    val name: String?
)