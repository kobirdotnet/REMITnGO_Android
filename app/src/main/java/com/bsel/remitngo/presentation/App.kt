package com.bsel.remitngo.presentation

import android.app.Application
import com.bsel.remitngo.BuildConfig
import com.bsel.remitngo.presentation.di.Injector
import com.bsel.remitngo.presentation.di.core.*
import com.bsel.remitngo.presentation.di.login.LoginSubComponent
import com.bsel.remitngo.presentation.di.registration.RegistrationSubComponent

class App : Application(), Injector {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .netModule(NetModule(BuildConfig.BASE_URL))
            .useCaseModule(UseCaseModule())
            .repositoryModule(RepositoryModule())
            .remoteDataModule(RemoteDataModule())
            .build()
    }

    override fun createRegistrationSubComponent(): RegistrationSubComponent {
        return appComponent.registrationSubComponent().create()
    }

    override fun createLoginSubComponent(): LoginSubComponent {
        return appComponent.loginSubComponent().create()
    }
}
