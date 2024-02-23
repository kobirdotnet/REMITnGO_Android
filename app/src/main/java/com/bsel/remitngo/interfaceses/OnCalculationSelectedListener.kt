package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.data.model.paying_agent.PayingAgentData

interface OnCalculationSelectedListener {
    fun onPayingAgentItemSelected(selectedItem: PayingAgentData)
}
