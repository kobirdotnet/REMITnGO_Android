package com.bsel.remitngo.data.model.gender


import com.google.gson.annotations.SerializedName

data class GenderItem(
//    @SerializedName("deviceId")
//    val deviceId: String?,
//    @SerializedName("dropdownId")
//    val dropdownId: Int?,
//    @SerializedName("param1")
//    val param1: Int?,
//    @SerializedName("param2")
//    val param2: Int?

    val gender: String
) {
    override fun toString(): String {
        return "$gender"
    }
}