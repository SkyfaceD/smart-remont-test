package org.skyfaced.smartremont.util.extensions

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> lazySafetyNone(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { initializer() }

@Suppress("FunctionName")
fun <T> MutableSharedLiveData() = MutableSharedFlow<T>(
    replay = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)