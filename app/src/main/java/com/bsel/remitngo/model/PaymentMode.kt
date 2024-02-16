package com.bsel.remitngo.model

data class PaymentMode(
    val name: String
) {
    override fun toString(): String {
        return "$name"
    }
}
