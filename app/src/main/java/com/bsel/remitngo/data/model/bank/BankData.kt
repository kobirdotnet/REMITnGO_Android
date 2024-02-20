package com.bsel.remitngo.data.model.bank


import com.google.gson.annotations.SerializedName

data class BankData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)