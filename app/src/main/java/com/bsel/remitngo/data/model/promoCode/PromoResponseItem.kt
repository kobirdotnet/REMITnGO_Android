package com.bsel.remitngo.data.model.promoCode


import com.google.gson.annotations.SerializedName

data class PromoResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val promoResponseData: PromoResponseData?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)