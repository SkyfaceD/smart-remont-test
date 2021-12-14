package org.skyfaced.smartremont.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.backToRoot
import com.github.terrakok.modo.replace
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.navigation.Screens
import org.skyfaced.smartremont.ui.common.BaseState

class SignInViewModel(
    private val modo: Modo,
    private val signInRepository: SignInRepository
) : ViewModel() {
    private val _signInState = MutableSharedFlow<BaseState<Nothing?>>()
    val signInState = _signInState.asSharedFlow()

    fun signIn(number: String, password: String) = viewModelScope.launch {
        signInRepository.signIn(number, password)
            .catch { _signInState.emit(BaseState.OnFailure(it.cause)) }
            .onStart { _signInState.emit(BaseState.OnLoading) }
            .collect {
                if (it.data != null && it.error.message.isNullOrEmpty()) {
                    signInRepository.saveTokens(it.data.accessToken, it.data.refreshToken)
                    _signInState.emit(BaseState.OnSuccess(null))
                } else {
                    _signInState.emit(BaseState.OnFailure(message = it.error.message))
                }
            }
    }

    /**
     * Navigation
     */
    fun showMultiScreen(selectedTab: Int = Screens.SHOP_TAB) {
        modo.backToRoot()
        modo.replace(Screens.MultiStack(selectedTab))
    }
}
