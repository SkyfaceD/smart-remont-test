package org.skyfaced.smartremont.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.skyfaced.smartremont.ui.MainViewModel
import org.skyfaced.smartremont.ui.signIn.SignInViewModel
import org.skyfaced.smartremont.ui.start.StartViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { StartViewModel(get()) }

    viewModel { SignInViewModel(get(), get()) }
}