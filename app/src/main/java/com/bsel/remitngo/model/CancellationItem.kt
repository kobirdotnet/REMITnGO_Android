package com.bsel.remitngo.model

data class CancellationItem(
    val cancellationCode: String
) {
    override fun toString(): String {
        return "$cancellationCode"
    }
}
