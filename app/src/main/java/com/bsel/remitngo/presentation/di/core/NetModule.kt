package com.bsel.remitngo.presentation.di.core

import com.bsel.remitngo.data.api.Config
import com.bsel.remitngo.data.api.REMITnGoService
import com.bsel.remitngo.data.api.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
class NetModule(private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptor()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.DYNAMIC_BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideREMITnGoService(retrofit: Retrofit): REMITnGoService {
        return retrofit.create(REMITnGoService::class.java)
    }

}