package com.bsel.remitngo.model

data class BankBranchItem(
    val bankBranchName: String
) {
    override fun toString(): String {
        return "$bankBranchName"
    }
}
