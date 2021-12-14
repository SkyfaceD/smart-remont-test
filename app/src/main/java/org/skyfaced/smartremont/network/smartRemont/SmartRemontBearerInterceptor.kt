package org.skyfaced.smartremont.network.smartRemont

import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.skyfaced.smartremont.util.ApplicationPreferences

//TODO Refresh Token
class SmartRemontBearerInterceptor : Interceptor, KoinComponent {
    private val preferences by inject<ApplicationPreferences>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (preferences.accessToken.isNotEmpty()) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer ${preferences.accessToken}")
                .build()

            response.close()
            return chain.proceed(newRequest)
        }

        return response
    }
}