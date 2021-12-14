package org.skyfaced.smartremont.di.modules

import org.koin.dsl.module
import org.skyfaced.smartremont.ui.signIn.SignInRepository
import org.skyfaced.smartremont.ui.signIn.SignInRepositoryImpl
import org.skyfaced.smartremont.ui.signUp.SignUpRepository
import org.skyfaced.smartremont.ui.signUp.SignUpRepositoryImpl

val repositoryModule = module {
    single<SignInRepository> { SignInRepositoryImpl(get(), get()) }

    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
}