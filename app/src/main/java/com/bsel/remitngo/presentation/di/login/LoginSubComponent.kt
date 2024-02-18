package com.bsel.remitngo.presentation.di.login

import com.bsel.remitngo.presentation.ui.login.LoginActivity
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginSubComponent {
    fun inject(loginActivity: LoginActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginSubComponent
    }
}
