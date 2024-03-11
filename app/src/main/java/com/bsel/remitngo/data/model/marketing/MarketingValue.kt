package com.bsel.remitngo.data.model.marketing


import com.google.gson.annotations.SerializedName

data class MarketingValue(
    @SerializedName("rdoEmail")
    val rdoEmail: Boolean?,
    @SerializedName("rdoSMS")
    val rdoSMS: Boolean?,
    @SerializedName("rdoPhone")
    val rdoPhone: Boolean?,
    @SerializedName("rdoPost")
    val rdoPost: Boolean?
)