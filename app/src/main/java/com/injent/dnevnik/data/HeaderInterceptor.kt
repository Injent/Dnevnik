package com.injent.dnevnik.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    private val token: String?
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Access-Token", token!!)
                .build()
        )
    }
}