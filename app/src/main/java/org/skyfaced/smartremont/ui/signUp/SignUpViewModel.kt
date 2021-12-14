package org.skyfaced.smartremont.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.backToRoot
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.model.info.SignUpInfo

class SignUpViewModel(
    private val modo: Modo,
    private val signUpRepository: SignUpRepository
) : ViewModel() {
    private val _signUpState = MutableSharedFlow<SignUpState>()
    val signUpState = _signUpState.asSharedFlow()

    private var _signUpInfo = SignUpInfo()

    fun registerNumber(phoneNumber: String) = viewModelScope.launch {
        val signUp = SignUp.Register
        signUpRepository.registerNumber(phoneNumber)
            .catch { _signUpState.emit(SignUpState.OnFailure(it.cause)) }
            .onStart { _signUpState.emit(SignUpState.OnLoading(signUp)) }
            .collect {
                if (it.data != null && it.response) {
                    _signUpInfo = _signUpInfo.copy(id = it.data.id)
                    _signUpState.emit(SignUpState.OnSuccess(signUp, it.data.id))
                } else {
                    _signUpState.emit(SignUpState.OnFailure(message = it.error.message))
                }
            }
    }

    fun getSMSCode(id: Int) = viewModelScope.launch {
        val signUp = SignUp.SMSCode
        signUpRepository.getSMSCode(id)
            .catch { _signUpState.emit(SignUpState.OnFailure(it.cause)) }
            .onStart { _signUpState.emit(SignUpState.OnLoading(signUp)) }
            .collect {
                if (it.data != null && it.response) {
                    _signUpInfo = _signUpInfo.copy(smsCode = it.data.smsCode)
                    _signUpState.emit(SignUpState.OnSuccess(signUp, it.data.smsCode))
                } else {
                    _signUpState.emit(SignUpState.OnFailure(message = it.error.message))
                }
            }
    }

    fun verify(smsCode: String) = viewModelScope.launch {
        val id = _signUpInfo.id ?: return@launch _signUpState.emit(SignUpState.OnFailure())
        val signUp = SignUp.Verify
        signUpRepository.verify(id, smsCode)
            .catch { _signUpState.emit(SignUpState.OnFailure(it.cause)) }
            .onStart { _signUpState.emit(SignUpState.OnLoading(signUp)) }
            .collect {
                if (it.data != null && it.response) {
                    _signUpInfo = _signUpInfo.copy(
                        accessToken = it.data.accessToken,
                        refreshToken = it.data.refreshToken
                    )
                    _signUpState.emit(SignUpState.OnSuccess(signUp, it.data))
                } else {
                    _signUpState.emit(SignUpState.OnFailure(message = it.error.message))
                }
            }
    }

    fun setPassword(password: String) = viewModelScope.launch {
        val signUp = SignUp.Password
        signUpRepository.setPassword(password)
            .catch { _signUpState.emit(SignUpState.OnFailure(it.cause)) }
            .onStart { _signUpState.emit(SignUpState.OnLoading(signUp)) }
            .collect {
                if (it.response) {
                    _signUpState.emit(SignUpState.OnSuccess(signUp, null))
                } else {
                    _signUpState.emit(SignUpState.OnFailure(message = it.error.message))
                }
            }
    }

    /**
     * Navigation
     */
    fun navigateBackToRoot() = modo.backToRoot()
}