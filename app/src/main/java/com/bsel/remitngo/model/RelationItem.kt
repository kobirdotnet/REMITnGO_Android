package com.bsel.remitngo.model

data class RelationItem(
    val relationName: String
) {
    override fun toString(): String {
        return "$relationName"
    }
}
