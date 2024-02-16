package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.api_service.REMITnGoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String) {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideREMITnGoService(retrofit: Retrofit): REMITnGoService {
        return retrofit.create(REMITnGoService::class.java)
    }
}
