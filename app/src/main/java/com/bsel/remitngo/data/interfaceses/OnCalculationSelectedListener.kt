package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.paying_agent.PayingAgentData

interface OnCalculationSelectedListener {
    fun onPayingAgentBankItemSelected(selectedItem: PayingAgentData)
    fun onPayingAgentWalletItemSelected(selectedItem: PayingAgentData)
}
