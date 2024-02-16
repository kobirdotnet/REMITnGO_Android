package com.bsel.remitngo.model

data class Category(
    val categoryName: String
) {
    override fun toString(): String {
        return "$categoryName"
    }
}
