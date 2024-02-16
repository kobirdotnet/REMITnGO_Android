package com.bsel.remitngo.data.api_service

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val getToken: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val token = getToken()?.let {it}

        val modifiedRequest: Request = if (token != null) {
            originalRequest.newBuilder()
                .header("Basic", token)
                .build()
        } else {
            originalRequest
        }
        return chain.proceed(modifiedRequest)
    }
}