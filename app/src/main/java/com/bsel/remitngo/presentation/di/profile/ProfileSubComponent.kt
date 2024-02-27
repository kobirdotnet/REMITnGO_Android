package com.bsel.remitngo.presentation.di.profile

import com.bsel.remitngo.presentation.ui.profile.ProfileFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileSubComponent {

    fun inject(profileFragment: ProfileFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileSubComponent
    }

}
