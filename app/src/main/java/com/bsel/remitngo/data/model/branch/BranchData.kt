package com.bsel.remitngo.data.model.branch


import com.google.gson.annotations.SerializedName

data class BranchData(
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("Name")
    val name: String?
)