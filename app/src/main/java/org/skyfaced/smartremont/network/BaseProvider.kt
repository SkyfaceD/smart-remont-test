package org.skyfaced.smartremont.network

import android.annotation.SuppressLint
import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import logcat.logcat
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@OptIn(ExperimentalSerializationApi::class)
open class BaseProvider(private val application: Application) {
    companion object {
        const val DEFAULT_TIMEOUT_SECONDS = 15L
    }

    private val serializer = Json { explicitNulls = false }
    private val converter = serializer.asConverterFactory("application/json".toMediaType())

    private val logcatLoggingInterceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            logcat("OkHttp") { message }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val cache: Cache
        get() {
            val cacheSize = 10 * 1024 * 1024
            return Cache(application.cacheDir, cacheSize.toLong())
        }

    protected val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(logcatLoggingInterceptor)
        .cache(cache)

    /**
     * Use only for test purposes
     */
    protected val unsafeClientBuilder: OkHttpClient.Builder
        get() {
            return try {
                val trustAllCerts: Array<TrustManager> = arrayOf(
                    @SuppressLint("CustomX509TrustManager")
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                            // Ignore
                        }

                        override fun checkServerTrusted(
                            chain: Array<out X509Certificate>?,
                            authType: String?
                        ) {
                            // Ignore
                        }

                        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
                    }
                )

                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                clientBuilder
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .hostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                clientBuilder
            }
        }

    protected val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(converter)
}