package com.bsel.remitngo.model

data class Document(
    val documentName: String
) {
    override fun toString(): String {
        return "$documentName"
    }
}
