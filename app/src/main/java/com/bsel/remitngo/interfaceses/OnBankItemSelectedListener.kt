package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.model.BankBranchItem
import com.bsel.remitngo.model.BankItem
import com.bsel.remitngo.model.DivisionItem

interface OnBankItemSelectedListener {
    fun onBankItemSelected(selectedItem: BankItem)
    fun onDivisionItemSelected(selectedItem: DivisionItem)
    fun onBankBranchItemSelected(selectedItem: BankBranchItem)
}
