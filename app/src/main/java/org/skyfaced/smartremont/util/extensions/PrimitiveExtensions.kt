package org.skyfaced.smartremont.util.extensions

fun <T> lazySafetyNone(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { initializer() }