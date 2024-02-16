package com.bsel.remitngo.model

data class TransactionItem(
    val transactionName: String
) {
    override fun toString(): String {
        return "$transactionName"
    }
}
