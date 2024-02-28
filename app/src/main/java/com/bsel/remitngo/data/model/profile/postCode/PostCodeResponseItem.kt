package com.bsel.remitngo.data.model.profile.postCode


import com.google.gson.annotations.SerializedName

data class PostCodeResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<PostCodeData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: String?
)