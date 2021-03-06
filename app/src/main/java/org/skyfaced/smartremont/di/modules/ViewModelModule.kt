package org.skyfaced.smartremont.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.skyfaced.smartremont.ui.MainViewModel
import org.skyfaced.smartremont.ui.gallery.GalleryViewModel
import org.skyfaced.smartremont.ui.shop.details.DetailsViewModel
import org.skyfaced.smartremont.ui.shop.shops.ShopsViewModel
import org.skyfaced.smartremont.ui.signIn.SignInViewModel
import org.skyfaced.smartremont.ui.signUp.SignUpViewModel
import org.skyfaced.smartremont.ui.start.StartViewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { StartViewModel(get()) }

    viewModel { SignInViewModel(get(), get()) }

    viewModel { SignUpViewModel(get(), get()) }

    viewModel { ShopsViewModel(get(), get()) }

    viewModel { (shopId: Int, cityId: Int) -> DetailsViewModel(get(), get(), shopId, cityId) }

    viewModel { GalleryViewModel(get()) }
}