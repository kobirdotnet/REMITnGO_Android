package com.bsel.remitngo.model

data class AddressItem(
    val addressName: String
) {
    override fun toString(): String {
        return "$addressName"
    }
}
