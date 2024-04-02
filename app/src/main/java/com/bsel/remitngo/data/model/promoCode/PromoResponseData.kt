package com.bsel.remitngo.data.model.promoCode


import com.google.gson.annotations.SerializedName

data class PromoResponseData(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("PromoData")
    val promoData: PromoData?
)