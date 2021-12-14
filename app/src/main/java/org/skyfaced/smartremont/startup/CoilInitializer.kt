package org.skyfaced.smartremont.startup

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import coil.util.DebugLogger
import logcat.logcat
import okhttp3.OkHttpClient
import org.skyfaced.smartremont.network.BaseProvider
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class CoilInitializer : Initializer<Unit> {
    private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        .connectTimeout(BaseProvider.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(BaseProvider.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(BaseProvider.DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)

    private val unsafeClientBuilder: OkHttpClient.Builder
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

    override fun create(context: Context) {
        val cacheClient = unsafeClientBuilder
            .cache(CoilUtils.createDefaultCache(context))
            .build()
        val imageLoader = ImageLoader.Builder(context)
            .logger(DebugLogger())
            .crossfade(true)
            .okHttpClient { cacheClient }
            .build()
        Coil.setImageLoader(imageLoader)

        logcat { "Coil Initialized" }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LogCatInitializer::class.java)
    }
}