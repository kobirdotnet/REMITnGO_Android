package com.bsel.remitngo.data.model.promoCode


import com.google.gson.annotations.SerializedName

data class PromoData(
    @SerializedName("ApplyPromoAmtFor")
    val applyPromoAmtFor: Int?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("Commision")
    val commision: Double?,
    @SerializedName("ModifiedBeneAmount")
    val modifiedBeneAmount: Double?,
    @SerializedName("ModifiedCommision")
    val modifiedCommision: Double?,
    @SerializedName("ModifiedRate")
    val modifiedRate: Double?,
    @SerializedName("ModifiedSendAmount")
    val modifiedSendAmount: Double?,
    @SerializedName("PromoMsg")
    val promoMsg: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SendAmount")
    val sendAmount: Double?
)