package org.skyfaced.smartremont.ui.signUp

sealed class SignUpState {
    data class OnLoading(val state: SignUp) : SignUpState()

    data class OnSuccess<T>(val state: SignUp, val data: T) : SignUpState()

    data class OnFailure(val cause: Throwable? = null, val message: String? = null) : SignUpState()

    override fun toString(): String {
        return when (this) {
            is OnFailure -> "Failure $cause $message"
            is OnLoading -> "Loading $state"
            is OnSuccess<*> -> "Success $state $data"
        }
    }
}