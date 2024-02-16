package com.bsel.remitngo.model

data class AnnualIncome(
    val annualIncomeName: String
) {
    override fun toString(): String {
        return "$annualIncomeName"
    }
}
