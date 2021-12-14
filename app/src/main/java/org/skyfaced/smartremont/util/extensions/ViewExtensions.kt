package org.skyfaced.smartremont.util.extensions

import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.skyfaced.smartremont.util.ui.DebouncedOnClickListener

fun ViewBinding.showSnack(
    message: String,
    @BaseTransientBottomBar.Duration length: Int = Snackbar.LENGTH_SHORT,
    anchorView: View? = null,
) = Snackbar.make(root, message, length).apply {
    setAnchorView(anchorView)
    show()
}

fun View.showSnack(
    message: String,
    @BaseTransientBottomBar.Duration length: Int = Snackbar.LENGTH_SHORT,
    anchorView: View? = null,
) = Snackbar.make(this, message, length).apply {
    setAnchorView(anchorView)
    show()
}

fun ViewBinding.showSnack(
    @StringRes strRes: Int,
    @BaseTransientBottomBar.Duration length: Int = Snackbar.LENGTH_SHORT,
    anchorView: View? = null,
) = Snackbar.make(root, strRes, length).apply {
    setAnchorView(anchorView)
    show()
}

fun View.showSnack(
    @StringRes strRes: Int,
    @BaseTransientBottomBar.Duration length: Int = Snackbar.LENGTH_SHORT,
    anchorView: View? = null,
) = Snackbar.make(this, strRes, length).apply {
    setAnchorView(anchorView)
    show()
}

/**
 * https://github.com/gnoemes/Shikimori-App-Remastered/blob/dev/app/src/main/java/com/gnoemes/shikimori/utils/ViewExtenstions.kt
 */
inline fun View.setOnDebounceClickListener(
    delay: Long = 300L,
    crossinline body: (v: View) -> Unit
) {
    setOnClickListener(org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener(delay) {
        body(this)
    })
}

inline fun setOnDebounceClickListener(delay: Long = 300L, crossinline body: (v: View) -> Unit) =
    object : DebouncedOnClickListener(delay) {
        override fun onDebouncedClick(v: View?) {
            if (v != null) body(v)
        }
    }

inline fun BottomSheetBehavior<*>.hideCallback(crossinline action: () -> Unit) =
    addBottomSheetCallback(
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) action()
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Ignore
            }
        }
    )