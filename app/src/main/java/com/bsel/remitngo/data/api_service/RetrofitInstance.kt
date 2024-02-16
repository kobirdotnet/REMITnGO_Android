package com.bsel.remitngo.data.api_service

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://rnguat.bracsaajanexchange.com/"

        private val interceptor = HttpLoggingInterceptor { message ->
            Log.i("OkHttp", message)
        }.apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private var token: String? = null

        // Function to set the token after login
        fun setToken(newToken: String) {
            token = newToken
        }

        // Function to get the token
        fun getToken(): String? {
            return token
        }

        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .addInterceptor(TokenInterceptor { getToken() }) // Use the dynamic token approach
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}