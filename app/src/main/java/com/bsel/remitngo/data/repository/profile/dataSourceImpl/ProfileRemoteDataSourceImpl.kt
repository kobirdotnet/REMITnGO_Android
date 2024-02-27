package com.bsel.remitngo.data.repository.profile.dataSourceImpl

import com.bsel.remitngo.data.api.REMITnGoService
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
import com.bsel.remitngo.data.repository.profile.dataSource.ProfileRemoteDataSource
import retrofit2.Response

class ProfileRemoteDataSourceImpl(private val remitNgoService: REMITnGoService) :
    ProfileRemoteDataSource {

    override suspend fun profile(profileItem: ProfileItem): Response<ProfileResponseItem> {
        return remitNgoService.profile(profileItem)
    }

    override suspend fun annualIncome(annualIncomeItem: AnnualIncomeItem): Response<AnnualIncomeResponseItem> {
        return remitNgoService.annualIncome(annualIncomeItem)
    }

    override suspend fun sourceOfIncome(sourceOfIncomeItem: SourceOfIncomeItem): Response<SourceOfIncomeResponseItem> {
        return remitNgoService.sourceOfIncome(sourceOfIncomeItem)
    }

    override suspend fun occupationType(occupationTypeItem: OccupationTypeItem): Response<OccupationTypeResponseItem> {
        return remitNgoService.occupationType(occupationTypeItem)
    }

    override suspend fun occupation(occupationItem: OccupationItem): Response<OccupationResponseItem> {
        return remitNgoService.occupation(occupationItem)
    }

    override suspend fun nationality(nationalityItem: NationalityItem): Response<NationalityResponseItem> {
        return remitNgoService.nationality(nationalityItem)
    }

}







