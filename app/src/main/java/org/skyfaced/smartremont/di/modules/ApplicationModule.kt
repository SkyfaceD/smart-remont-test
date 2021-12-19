package org.skyfaced.smartremont.di.modules

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.skyfaced.smartremont.navigation.ApplicationNavigation
import org.skyfaced.smartremont.network.smartRemont.MockProvider
import org.skyfaced.smartremont.network.smartRemont.ProductionProvider
import org.skyfaced.smartremont.util.ApplicationPreferences
import org.skyfaced.smartremont.util.FileHelper

val applicationModule = module(createdAtStart = true) {
    single { ApplicationPreferences(androidApplication()) }

    single { ApplicationNavigation(androidApplication()).modo }

    single { MockProvider(androidApplication()) }

    single { ProductionProvider(androidApplication()) }

    single { FileHelper(androidApplication()) }
}