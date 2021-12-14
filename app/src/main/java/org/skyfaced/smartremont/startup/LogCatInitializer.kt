package org.skyfaced.smartremont.startup

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import logcat.AndroidLogcatLogger
import logcat.logcat

class LogCatInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AndroidLogcatLogger.installOnDebuggableApp(context as Application)
        logcat { "LogCat Initialized" }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}