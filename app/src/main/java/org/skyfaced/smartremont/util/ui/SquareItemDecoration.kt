package org.skyfaced.smartremont.util.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class SquareItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(Rect(offset, offset, offset, offset))
    }
}