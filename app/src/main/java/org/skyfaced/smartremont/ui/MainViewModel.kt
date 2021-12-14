package org.skyfaced.smartremont.ui

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.android.ModoRender
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.github.terrakok.modo.selectStack

class MainViewModel(
    private val modo: Modo,
) : ViewModel() {
    /**
     * Navigation
     */
    fun initModo(savedInstanceState: Bundle?, screen: Screen) =
        modo.init(savedInstanceState, screen)

    fun setModoRender(modoRender: ModoRender?) {
        modo.render = modoRender
    }

    fun saveModoState(bundle: Bundle) = modo.saveState(bundle)

    fun onTabSelected(order: Int) = modo.selectStack(order)

    fun navigateBack() = modo.back()
}