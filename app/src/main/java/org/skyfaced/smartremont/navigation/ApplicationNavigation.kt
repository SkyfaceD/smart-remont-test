package org.skyfaced.smartremont.navigation

import android.app.Application
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.MultiReducer
import com.github.terrakok.modo.android.AppReducer
import com.github.terrakok.modo.android.LogReducer
import org.skyfaced.smartremont.BuildConfig

class ApplicationNavigation(application: Application) {
    val modo = Modo(
        if (BuildConfig.DEBUG) LogReducer(AppReducer(application, MultiReducer()))
        else AppReducer(application, MultiReducer())
    )
}