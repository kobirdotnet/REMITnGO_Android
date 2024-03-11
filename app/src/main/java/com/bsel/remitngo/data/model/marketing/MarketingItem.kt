package com.bsel.remitngo.data.model.marketing


import com.google.gson.annotations.SerializedName

data class MarketingItem(

    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("personId")
    val personId: Int?,
    @SerializedName("communicationPreferencesType")
    val communicationPreferencesType: Int?,
    @SerializedName("communicationPreferencesValue")
    val communicationPreferencesValue: Boolean?,
)