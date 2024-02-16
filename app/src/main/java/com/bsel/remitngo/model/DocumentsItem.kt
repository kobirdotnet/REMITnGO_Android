package com.bsel.remitngo.model

data class DocumentsItem(
    val documentsName: String
) {
    override fun toString(): String {
        return "$documentsName"
    }
}