package org.skyfaced.smartremont.di.modules

import org.koin.dsl.module
import org.skyfaced.smartremont.network.smartRemont.MockProvider
import org.skyfaced.smartremont.network.smartRemont.ProductionProvider
import org.skyfaced.smartremont.ui.gallery.GalleryRepository
import org.skyfaced.smartremont.ui.gallery.GalleryRepositoryImpl
import org.skyfaced.smartremont.ui.shop.details.DetailsRepository
import org.skyfaced.smartremont.ui.shop.details.DetailsRepositoryImpl
import org.skyfaced.smartremont.ui.shop.shops.ShopsRepository
import org.skyfaced.smartremont.ui.shop.shops.ShopsRepositoryImpl
import org.skyfaced.smartremont.ui.signIn.SignInRepository
import org.skyfaced.smartremont.ui.signIn.SignInRepositoryImpl
import org.skyfaced.smartremont.ui.signUp.SignUpRepository
import org.skyfaced.smartremont.ui.signUp.SignUpRepositoryImpl

val repositoryModule = module {
    single<SignInRepository> { SignInRepositoryImpl(get<MockProvider>().api, get()) }

    single<SignUpRepository> { SignUpRepositoryImpl(get<MockProvider>().api) }

    single<ShopsRepository> {
        ShopsRepositoryImpl(
            get<ProductionProvider>().api,
            get<MockProvider>().api,
            get()
        )
    }

    single<DetailsRepository> { DetailsRepositoryImpl(get<ProductionProvider>().api) }

    single<GalleryRepository> { GalleryRepositoryImpl(get()) }
}