package com.bsel.remitngo.domain.repository

import com.bsel.remitngo.data.model.profile.ProfileItem
import com.bsel.remitngo.data.model.profile.ProfileResponseItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeItem
import com.bsel.remitngo.data.model.profile.annualIncome.AnnualIncomeResponseItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityItem
import com.bsel.remitngo.data.model.profile.nationality.NationalityResponseItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationItem
import com.bsel.remitngo.data.model.profile.occupation.OccupationResponseItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeItem
import com.bsel.remitngo.data.model.profile.occupationType.OccupationTypeResponseItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeItem
import com.bsel.remitngo.data.model.profile.sourceOfIncome.SourceOfIncomeResponseItem

interface ProfileRepository {

    suspend fun profile(profileItem: ProfileItem): ProfileResponseItem?
    suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): AnnualIncomeResponseItem?
    suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem?
    suspend fun occupationType(occupationTypeItem: OccupationTypeItem): OccupationTypeResponseItem?
    suspend fun occupation(occupationItem: OccupationItem): OccupationResponseItem?
    suspend fun nationality(nationalityItem: NationalityItem): NationalityResponseItem?

}

