package com.bsel.remitngo.presentation.di.profile

import com.bsel.remitngo.bottomSheet.*
import com.bsel.remitngo.presentation.ui.profile.ProfileFragment
import com.bsel.remitngo.presentation.ui.profile.address.SaveAddressFragment
import com.bsel.remitngo.presentation.ui.profile.mobile.MobileNumberFragment
import com.bsel.remitngo.presentation.ui.profile.personal_information.PersonalInformationFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileSubComponent {

    fun inject(profileFragment: ProfileFragment)
    fun inject(personalInformationFragment: PersonalInformationFragment)
    fun inject(saveAddressFragment: SaveAddressFragment)
    fun inject(mobileNumberFragment: MobileNumberFragment)
    fun inject(occupationTypeBottomSheet: OccupationTypeBottomSheet)
    fun inject(occupationBottomSheet: OccupationBottomSheet)
    fun inject(sourceOfIncomeBottomSheet: SourceOfIncomeBottomSheet)
    fun inject(annualIncomeBottomSheet: AnnualIncomeBottomSheet)
    fun inject(nationalityBottomSheet: NationalityBottomSheet)
    fun inject(addressBottomSheet: AddressBottomSheet)
    fun inject(ukDivisionBottomSheet: UkDivisionBottomSheet)
    fun inject(countyBottomSheet: CountyBottomSheet)
    fun inject(cityBottomSheet: CityBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileSubComponent
    }

}
