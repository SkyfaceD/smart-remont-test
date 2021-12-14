package org.skyfaced.smartremont.util.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

//TODO Customize last divider
class VerticalDivider(
    private val height: Int,
    private val color: Int,
    private val excludeLast: Boolean = true,
    private val startOffset: Int = 0,
    private val endOffset: Int = 0
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = this@VerticalDivider.color
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val pos = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }
        val itemCount = parent.adapter?.itemCount?.dec() ?: return

        if (pos == itemCount) outRect.setEmpty()
        else outRect.set(0, 0, 0, height)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let { adapter ->
            parent.children.forEach { view ->
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                val itemCount = adapter.itemCount.dec()
                if (position != itemCount) {
                    c.drawRect(
                        view.left.toFloat() + startOffset,
                        view.bottom.toFloat(),
                        view.right.toFloat() - endOffset,
                        view.bottom.toFloat() + height,
                        paint
                    )
                }
            }
        }
    }
}