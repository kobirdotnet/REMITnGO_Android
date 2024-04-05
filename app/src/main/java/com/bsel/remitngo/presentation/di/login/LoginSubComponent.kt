package com.bsel.remitngo.presentation.di.login

import com.bsel.remitngo.bottomSheet.ForgotPasswordBottomSheet
import com.bsel.remitngo.presentation.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.Subcomponent

@LoginScope
@Subcomponent(modules = [LoginModule::class])
interface LoginSubComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(forgotPasswordBottomSheet: ForgotPasswordBottomSheet)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginSubComponent
    }
}
