package org.skyfaced.smartremont.di.modules

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.skyfaced.smartremont.navigation.ApplicationNavigation
import org.skyfaced.smartremont.network.smartRemont.SmartRemontProvider
import org.skyfaced.smartremont.util.ApplicationPreferences

val applicationModule = module(createdAtStart = true) {
    single { ApplicationPreferences(androidApplication()) }

    single { ApplicationNavigation(androidApplication()).modo }

    single { SmartRemontProvider(androidApplication()).api }
}