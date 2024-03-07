package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.cancel_request.cancel_reason.CancelReasonData

interface OnCancelReasonItemSelectedListener {
    fun onCancelReasonItemSelected(selectedItem: CancelReasonData)
}
