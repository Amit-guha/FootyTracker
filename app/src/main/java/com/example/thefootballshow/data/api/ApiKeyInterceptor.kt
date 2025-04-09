package com.example.thefootballshow.data.api

import com.example.thefootballshow.di.ApiKey
import okhttp3.Interceptor
import okhttp3.Response

//https://www.linkedin.com/pulse/interceptors-okhttp-mohamad-abuzaid/
class ApiKeyInterceptor(@ApiKey private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header("X-Auth-Token", apiKey)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}