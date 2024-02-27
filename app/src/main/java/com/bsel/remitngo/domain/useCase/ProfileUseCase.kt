package com.bsel.remitngo.domain.useCase

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
import com.bsel.remitngo.domain.repository.ProfileRepository

class ProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend fun execute(profileItem: ProfileItem): ProfileResponseItem? {
        return profileRepository.profile(profileItem)
    }

    suspend fun execute(annualIncomeItem: AnnualIncomeItem): AnnualIncomeResponseItem? {
        return profileRepository.annualIncome(annualIncomeItem)
    }
    suspend fun execute(sourceOfIncomeItem: SourceOfIncomeItem): SourceOfIncomeResponseItem? {
        return profileRepository.sourceOfIncome(sourceOfIncomeItem)
    }

    suspend fun execute(occupationTypeItem: OccupationTypeItem): OccupationTypeResponseItem? {
        return profileRepository.occupationType(occupationTypeItem)
    }

    suspend fun execute(occupationItem: OccupationItem): OccupationResponseItem? {
        return profileRepository.occupation(occupationItem)
    }

    suspend fun execute(nationalityItem: NationalityItem): NationalityResponseItem? {
        return profileRepository.nationality(nationalityItem)
    }

}

