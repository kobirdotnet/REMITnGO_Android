package com.bsel.remitngo.model

data class SourceOfIncome(
    val sourceOfIncomeName: String
) {
    override fun toString(): String {
        return "$sourceOfIncomeName"
    }
}
