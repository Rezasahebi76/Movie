package com.android.movie.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $TOKEN")
                .build()
        )
    }

    companion object {
        private const val TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkZGFlMTBlNTdhNjhhM2ExZWQ5NzQ2NjAzYzcyNTc5NyIsInN1YiI6IjY1YzNlY2M1MjQ3NmYyMDE0OWZlNDczZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.eXs70tcdxWzq7pEqhfQ3NAddwaYMijxfXSkphkVWqZ4"
    }
}