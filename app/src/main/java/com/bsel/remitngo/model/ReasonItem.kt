package com.bsel.remitngo.model

data class ReasonItem(
    val reasonName: String
) {
    override fun toString(): String {
        return "$reasonName"
    }
}
