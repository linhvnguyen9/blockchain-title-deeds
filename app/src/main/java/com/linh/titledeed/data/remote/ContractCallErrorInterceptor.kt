package com.linh.titledeed.data.remote

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.linh.titledeed.data.entity.ContractCallErrorResponse
import com.linh.titledeed.data.entity.TokenOwnerException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.lang.Exception

class ContractCallErrorInterceptor(private val gson: Gson): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        try {
            val response = gson.fromJson(originalResponse.peekBody(2048).string(), ContractCallErrorResponse::class.java)
            Timber.d("ContractCallErrorInterceptor Got error response from contract")
            response.error?.let {
                when (response.error.code) {
                    -32000 -> {
                        if (response.error.message.contains("transfer caller is not owner nor approved")) {
                            Timber.d("ContractCallErrorInterceptor got owner exception")
                            throw TokenOwnerException(response.error.message, response.error.code)
                        }
                    }
                }
            }
        } catch (e: JsonSyntaxException) {
            return originalResponse
        }
        return originalResponse
    }
}