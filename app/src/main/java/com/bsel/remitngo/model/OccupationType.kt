package com.bsel.remitngo.model

data class OccupationType(
    val occupationTypeName: String
) {
    override fun toString(): String {
        return "$occupationTypeName"
    }
}
