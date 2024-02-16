package com.bsel.remitngo.model

data class CancelRequestItem(
    val cancelRequestName: String
) {
    override fun toString(): String {
        return "$cancelRequestName"
    }
}
