package com.bsel.remitngo.data.repository.profile.dataSource

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
import retrofit2.Response

interface ProfileRemoteDataSource {

    suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem>

    suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem>

    suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem>

    suspend fun occupationType(occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem>

    suspend fun occupation(occupationItem: OccupationItem): Response<OccupationResponseItem>

    suspend fun nationality(nationalityItem: NationalityItem): Response<NationalityResponseItem>

}





