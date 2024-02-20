package com.bsel.remitngo.data.model.branch


import com.google.gson.annotations.SerializedName

data class BranchResponseItem(
    @SerializedName("Code")
    val code: String?,
    @SerializedName("Data")
    val `data`: List<BranchData?>?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Token")
    val token: Any?
)