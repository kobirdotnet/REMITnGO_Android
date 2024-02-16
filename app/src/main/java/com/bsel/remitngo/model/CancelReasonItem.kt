package com.bsel.remitngo.model

data class CancelReasonItem(
    val cancelReasonName: String
) {
    override fun toString(): String {
        return "$cancelReasonName"
    }
}
