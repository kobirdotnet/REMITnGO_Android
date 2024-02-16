package com.bsel.remitngo.model

data class StatusItem(
    val status: String
) {
    override fun toString(): String {
        return "$status"
    }
}
