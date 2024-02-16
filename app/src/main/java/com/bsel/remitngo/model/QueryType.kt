package com.bsel.remitngo.model

data class QueryType(
    val queryTypeName: String
) {
    override fun toString(): String {
        return "$queryTypeName"
    }
}
