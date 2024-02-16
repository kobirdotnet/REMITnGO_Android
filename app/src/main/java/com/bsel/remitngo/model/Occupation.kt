package com.bsel.remitngo.model

data class Occupation(
    val occupationName: String
) {
    override fun toString(): String {
        return "$occupationName"
    }
}
