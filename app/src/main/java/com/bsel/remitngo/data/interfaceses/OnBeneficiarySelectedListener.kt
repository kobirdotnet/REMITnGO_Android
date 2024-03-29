package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.relation.RelationData

interface OnBeneficiarySelectedListener {
    fun onRelationItemSelected(selectedItem: RelationData)
    fun onReasonItemSelected(selectedItem: ReasonData)
}
