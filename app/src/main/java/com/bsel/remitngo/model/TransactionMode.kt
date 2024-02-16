package com.bsel.remitngo.model

data class TransactionMode(
    val name: String
) {
    override fun toString(): String {
        return "$name"
    }
}

