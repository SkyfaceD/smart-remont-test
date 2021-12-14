package org.skyfaced.smartremont.ui.start

import androidx.lifecycle.ViewModel
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import com.github.terrakok.modo.replace
import org.skyfaced.smartremont.navigation.Screens

class StartViewModel(
    val modo: Modo
) : ViewModel() {
    /**
     * Navigation
     */
    fun replaceWithMultiScreen(selectedTab: Int = Screens.SHOP_TAB) =
        modo.replace(Screens.MultiStack(selectedTab))

    fun navigateToSignUpScreen() = modo.forward(Screens.SignUpScreen())

    fun navigateToSignInScreen() = modo.forward(Screens.SignInScreen())
}