package org.skyfaced.smartremont.network.smartRemont

import android.app.Application
import okhttp3.OkHttpClient
import org.skyfaced.smartremont.network.BaseProvider
import retrofit2.Retrofit

class ProductionProvider(application: Application) : BaseProvider(application) {
    private companion object {
        const val PRODUCTION_BASE_URL = "https://testapi.smartremarket.kz/index/"
    }

    val api: SmartRemontApi = retrofit.create(SmartRemontApi::class.java)

    private val client: OkHttpClient
        get() = clientBuilder
            .addInterceptor(SmartRemontBearerInterceptor())
            .build()

    private val retrofit: Retrofit
        get() = retrofitBuilder
            .baseUrl(PRODUCTION_BASE_URL)
            .client(client)
            .build()
}