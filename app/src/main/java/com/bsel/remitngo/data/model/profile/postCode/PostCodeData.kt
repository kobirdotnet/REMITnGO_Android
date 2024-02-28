package com.bsel.remitngo.data.model.profile.postCode


import com.google.gson.annotations.SerializedName

data class PostCodeData(
    @SerializedName("pId")
    val pId: String?,
    @SerializedName("PostTown")
    val postTown: String?,
    @SerializedName("Postcode")
    val postcode: String?,
    @SerializedName("subbuildingname")
    val subbuildingname: String?,
    @SerializedName("Thoroughfare")
    val thoroughfare: String?,
    @SerializedName("UkAddress")
    val ukAddress: String?
)