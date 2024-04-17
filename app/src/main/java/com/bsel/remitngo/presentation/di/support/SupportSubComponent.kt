package com.bsel.remitngo.presentation.di.support

import com.bsel.remitngo.presentation.ui.support.SupportFragment
import dagger.Subcomponent

@SupportScope
@Subcomponent(modules = [SupportModule::class])
interface SupportSubComponent {

    fun inject(supportFragment: SupportFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): SupportSubComponent
    }

}
