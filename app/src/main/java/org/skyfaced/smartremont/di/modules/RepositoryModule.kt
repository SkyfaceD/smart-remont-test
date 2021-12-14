package org.skyfaced.smartremont.di.modules

import org.koin.dsl.module
import org.skyfaced.smartremont.network.smartRemont.MockProvider
import org.skyfaced.smartremont.network.smartRemont.ProductionProvider
import org.skyfaced.smartremont.ui.shop.cities.CitiesRepository
import org.skyfaced.smartremont.ui.shop.cities.CitiesRepositoryImpl
import org.skyfaced.smartremont.ui.signIn.SignInRepository
import org.skyfaced.smartremont.ui.signIn.SignInRepositoryImpl
import org.skyfaced.smartremont.ui.signUp.SignUpRepository
import org.skyfaced.smartremont.ui.signUp.SignUpRepositoryImpl

val repositoryModule = module {
    single<SignInRepository> { SignInRepositoryImpl(get<MockProvider>().api, get()) }

    single<SignUpRepository> { SignUpRepositoryImpl(get<MockProvider>().api) }

    single<CitiesRepository> { CitiesRepositoryImpl(get<ProductionProvider>().api) }
}