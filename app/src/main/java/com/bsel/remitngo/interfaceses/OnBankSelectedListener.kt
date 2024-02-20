package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.data.model.bank.BankData
import com.bsel.remitngo.data.model.branch.BranchData
import com.bsel.remitngo.data.model.district.DistrictData
import com.bsel.remitngo.data.model.division.DivisionData

interface OnBankSelectedListener {
    fun onBankItemSelected(selectedItem: BankData)
    fun onDivisionItemSelected(selectedItem: DivisionData)
    fun onDistrictItemSelected(selectedItem: DistrictData)
    fun onBranchItemSelected(selectedItem: BranchData)
}
