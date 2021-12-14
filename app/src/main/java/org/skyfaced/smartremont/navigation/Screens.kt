package org.skyfaced.smartremont.navigation

import androidx.fragment.app.Fragment
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import kotlinx.parcelize.Parcelize
import org.skyfaced.smartremont.ui.start.StartFragment
import org.skyfaced.smartremont.ui.web.WebFragment

object Screens {
    private var currentScreenKey: String = StartFragment.SCREEN_KEY
    val CURRENT_SCREEN_KEY get() = currentScreenKey

    @Parcelize
    class StartScreen : AppScreen(StartFragment.SCREEN_KEY) {
        override fun create(): Fragment {
            currentScreenKey = id
            return StartFragment()
        }
    }

    const val MULTI_STACK_SCREEN_ID = "MultiStackScreen"

    const val SHOP_TAB = 0
    const val WEB_TAB = 1
    const val GALLERY_TAB = 2

    private val multiStackScreens = listOf(
        ShopScreen(),
        WebScreen(),
        GalleryScreen()
    )

    fun MultiStack(selectedTab: Int = SHOP_TAB) = MultiAppScreen(
        MULTI_STACK_SCREEN_ID,
        multiStackScreens,
        selectedTab
    )

    @Parcelize
    class ShopScreen : AppScreen(StartFragment.SCREEN_KEY) {
        override fun create(): Fragment {
            currentScreenKey = id
            return StartFragment()
        }
    }

    @Parcelize
    class WebScreen : AppScreen(WebFragment.SCREEN_KEY) {
        override fun create(): Fragment {
            currentScreenKey = id
            return WebFragment()
        }
    }

    @Parcelize
    class GalleryScreen : AppScreen(StartFragment.SCREEN_KEY) {
        override fun create(): Fragment {
            currentScreenKey = id
            return StartFragment()
        }
    }
}