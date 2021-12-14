package org.skyfaced.smartremont.network.smartRemont

import android.app.Application
import okhttp3.OkHttpClient
import org.skyfaced.smartremont.network.BaseProvider
import retrofit2.Retrofit

class SmartRemontProvider(application: Application) : BaseProvider(application) {
    private companion object {
        const val BASE_URL = "https://private-anon-f83520c05e-restsmartdev.apiary-mock.com/index/"
    }

    val api: SmartRemontApi = retrofit.create(SmartRemontApi::class.java)

    private val client: OkHttpClient
        get() = clientBuilder.build()

    private val retrofit: Retrofit
        get() = retrofitBuilder
            .baseUrl(BASE_URL)
            .client(client)
            .build()
}