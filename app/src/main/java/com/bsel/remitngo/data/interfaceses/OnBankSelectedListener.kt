package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.bank.BankData
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.data.model.district.DistrictData
import com.bsel.remitngo.data.model.division.DivisionData

interface OnBankSelectedListener {
    fun onBankItemSelected(selectedItem: BankData)
    fun onBranchItemSelected(selectedItem: BranchData)
}
