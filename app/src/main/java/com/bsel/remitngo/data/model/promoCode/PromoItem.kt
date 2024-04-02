package com.bsel.remitngo.data.model.promoCode


import com.google.gson.annotations.SerializedName

data class PromoItem(
    @SerializedName("beneAmount")
    val beneAmount: Double?,
    @SerializedName("commision")
    val commision: Double?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("promoCode")
    val promoCode: String?,
    @SerializedName("rate")
    val rate: Double?,
    @SerializedName("sendAmount")
    val sendAmount: Double?
)