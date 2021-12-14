package org.skyfaced.smartremont.util.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> Fragment.flowObserver(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) = viewLifecycleOwner.lifecycleScope.launch {
    flow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect(collect)
}