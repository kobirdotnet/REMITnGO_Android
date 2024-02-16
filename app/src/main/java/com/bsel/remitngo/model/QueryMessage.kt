package com.bsel.remitngo.model

data class QueryMessage(
    val queryMessage: String
) {
    override fun toString(): String {
        return "$queryMessage"
    }
}