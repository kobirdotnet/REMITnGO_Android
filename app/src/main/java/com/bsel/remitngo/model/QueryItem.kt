package com.bsel.remitngo.model

data class QueryItem(
    val queryName: String
) {
    override fun toString(): String {
        return "$queryName"
    }
}
