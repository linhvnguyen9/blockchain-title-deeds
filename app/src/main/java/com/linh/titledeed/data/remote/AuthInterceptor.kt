package com.linh.titledeed.data.remote

import com.linh.titledeed.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticateRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.PINATA_JWT}").build()
        return chain.proceed(authenticateRequest)
    }
}