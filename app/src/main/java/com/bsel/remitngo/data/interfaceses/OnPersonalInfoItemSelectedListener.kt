package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeData
import com.bsel.remitngo.data.model.profile.nationality.NationalityData
import com.bsel.remitngo.data.model.profile.occupation.OccupationData
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeData
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeData

interface OnPersonalInfoItemSelectedListener{
    fun onOccupationTypeItemSelected(selectedItem: OccupationTypeData)
    fun onOccupationItemSelected(selectedItem: OccupationData)
    fun onSourceOfIncomeItemSelected(selectedItem: SourceOfIncomeData)
    fun onAnnualIncomeItemSelected(selectedItem: AnnualIncomeData)
    fun onNationalityItemSelected(selectedItem: NationalityData)
}