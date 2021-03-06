package org.skyfaced.smartremont.startup

import android.content.Context
import androidx.startup.Initializer
import logcat.logcat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.skyfaced.smartremont.BuildConfig
import org.skyfaced.smartremont.di.modules.applicationModule
import org.skyfaced.smartremont.di.modules.repositoryModule
import org.skyfaced.smartremont.di.modules.viewModelModule

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        val koinApplication = startKoin {
            androidContext(context)
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            modules(
                applicationModule,
                repositoryModule,
                viewModelModule,
            )
        }

        logcat { "Koin Initialized" }

        return koinApplication
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LogCatInitializer::class.java)
    }
}