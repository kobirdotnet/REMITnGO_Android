package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.data.model.query.query_type.QueryTypeData

interface OnQueryTypeItemSelectedListener {
    fun onQueryTypeItemSelected(selectedItem: QueryTypeData)
}
