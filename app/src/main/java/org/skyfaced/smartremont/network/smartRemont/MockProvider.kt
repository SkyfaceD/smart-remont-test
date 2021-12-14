package org.skyfaced.smartremont.network.smartRemont

import android.app.Application
import okhttp3.OkHttpClient
import org.skyfaced.smartremont.network.BaseProvider
import retrofit2.Retrofit

class MockProvider(application: Application) : BaseProvider(application) {
    private companion object {
        const val MOCK_BASE_URL =
            "https://private-anon-f83520c05e-restsmartdev.apiary-mock.com/index/"
    }

    val api: SmartRemontApi = retrofit.create(SmartRemontApi::class.java)

    private val client: OkHttpClient.Builder
        get() = clientBuilder
            .addInterceptor(SmartRemontBearerInterceptor())

    private val retrofit: Retrofit
        get() = retrofitBuilder
            .baseUrl(MOCK_BASE_URL)
            .client(client.build())
            .build()
}