package com.bsel.remitngo.model

data class Nationality(
    val nationalityName: String
) {
    override fun toString(): String {
        return "$nationalityName"
    }
}
