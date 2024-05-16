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
    @SerializedName("ModifiedCommision")
    val modifiedCommision: Double?,
    @SerializedName("ModifiedRate")
    val modifiedRate: Double?,
    @SerializedName("PromoMsg")
    val promoMsg: String?,
    @SerializedName("Rate")
    val rate: Double?,
    @SerializedName("SentAmount")
    val sentAmount: Double?,
    @SerializedName("TotalAmount")
    val totalAmount: Double?
)