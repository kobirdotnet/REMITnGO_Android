package com.bsel.remitngo.interfaceses

import com.bsel.remitngo.model.*

interface OnPersonalInfoItemSelectedListener{
    fun onOccupationTypeItemSelected(selectedItem: OccupationType)
    fun onOccupationItemSelected(selectedItem: Occupation)
    fun onSourceOfIncomeItemSelected(selectedItem: SourceOfIncome)
    fun onAnnualIncomeItemSelected(selectedItem: AnnualIncome)
    fun onNationalityItemSelected(selectedItem: Nationality)
}