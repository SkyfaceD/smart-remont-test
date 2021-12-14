package org.skyfaced.smartremont.ui.common

sealed class BaseState<out T> {
    object OnLoading : BaseState<Nothing>()

    data class OnSuccess<T>(val data: T) : BaseState<T>()

    data class OnFailure(val cause: Throwable? = null, val message: String? = null) :
        BaseState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is OnFailure -> "Failure $cause $message"
            is OnLoading -> "Loading"
            is OnSuccess -> "Success $data"
        }
    }
}