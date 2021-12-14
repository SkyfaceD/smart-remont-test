package org.skyfaced.smartremont.di.modules

import org.koin.dsl.module
import org.skyfaced.smartremont.ui.signIn.SignInRepository
import org.skyfaced.smartremont.ui.signIn.SignInRepositoryImpl

val repositoryModule = module {
    single<SignInRepository> { SignInRepositoryImpl(get(), get()) }
}