package com.bsel.remitngo.model

data class ChooseOrderType(
    val flagDrawable: Int,
    val orderTypeName: String
) {
    override fun toString(): String {
        return "$orderTypeName"
    }
}
