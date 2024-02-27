package com.bsel.remitngo.presentation.di.profile

import com.bsel.remitngo.domain.useCase.ProfileUseCase
import com.bsel.remitngo.presentation.ui.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {
    @ProfileScope
    @Provides
    fun provideProfileViewModelFactory(profileUseCase: ProfileUseCase): ProfileViewModelFactory {
        return ProfileViewModelFactory(profileUseCase)
    }
}