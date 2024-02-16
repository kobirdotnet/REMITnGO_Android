package com.bsel.remitngo.model

data class BankItem(
    val flagDrawable: Int,
    val bankName: String
) {
    override fun toString(): String {
        return "$bankName"
    }
}
