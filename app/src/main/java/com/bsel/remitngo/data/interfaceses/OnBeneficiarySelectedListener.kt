package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.beneficiary.beneficiary.GetBeneficiaryData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData
import com.bsel.remitngo.data.model.reason.ReasonData
import com.bsel.remitngo.data.model.relation.RelationData

interface OnBeneficiarySelectedListener {
    fun onChooseRecipientItemSelected(selectedItem: GetBeneficiaryData)
    fun onPurposeOfTransferItemSelected(selectedItem: ReasonData)
    fun onSourceOfFundItemSelected(selectedItem: SourceOfIncomeData)
}
