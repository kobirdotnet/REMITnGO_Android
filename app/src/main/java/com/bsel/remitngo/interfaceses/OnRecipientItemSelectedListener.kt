package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.model.ReasonItem
import com.bsel.remitngo.model.RelationItem

interface OnRecipientItemSelectedListener {
    fun onRelationItemSelected(selectedItem: RelationItem)
    fun onReasonItemSelected(selectedItem: ReasonItem)
}
