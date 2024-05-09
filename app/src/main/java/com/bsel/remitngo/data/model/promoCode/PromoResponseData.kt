package com.bsel.remitngo.data.model.promoCode


import com.google.gson.annotations.SerializedName

data class PromoResponseData(
    @SerializedName("ApplyPromoAmtForId")
    val applyPromoAmtForId: Int?,
    @SerializedName("BeneAmount")
    val beneAmount: Double?,
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Commision")
    val commision: Double?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("ModifiedBeneAmount")
    val modifiedBeneAmount: Double?,
    @SerializedName("ModifiedCommision")
    val modifiedCommision: Double?,
    @SerializedName("ModifiedRate")
    val modifiedRate: Double?,
    @SerializedName("ModifiedSentAmount")
    val modifiedSentAmount: Double?,
    @SerializedName("PromoMsg")
    val promoMsg: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SentAmount")
    val sentAmount: Double?
)