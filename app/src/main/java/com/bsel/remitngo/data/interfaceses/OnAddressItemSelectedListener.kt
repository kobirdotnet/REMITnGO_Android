package com.bsel.remitngo.data.interfaceses

import com.bsel.remitngo.data.model.profile.city.CityData
import com.bsel.remitngo.data.model.profile.county.CountyData
import com.bsel.remitngo.data.model.profile.postCode.PostCodeData
import com.bsel.remitngo.data.model.profile.uk_division.UkDivisionData

interface OnAddressItemSelectedListener {
    fun onAddressItemSelected(selectedItem: PostCodeData)
    fun onUkDivisionItemSelected(selectedItem: UkDivisionData)
    fun onCountyItemSelected(selectedItem: CountyData)
    fun onCityItemSelected(selectedItem: CityData)
}
