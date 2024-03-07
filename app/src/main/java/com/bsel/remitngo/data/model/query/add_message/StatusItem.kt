package com.bsel.remitngo.data.model.query.add_message

data class StatusItem(
    val status: String
) {
    override fun toString(): String {
        return "$status"
    }
}
