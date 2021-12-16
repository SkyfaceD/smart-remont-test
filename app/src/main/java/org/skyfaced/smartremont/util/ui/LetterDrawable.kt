package org.skyfaced.smartremont.util.ui

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.random.Random.Default.nextInt

//TODO Customize size
class LetterDrawable(word: String, textSize: Float = 52f, backgroundColor: Int? = null) :
    Drawable() {
    private val letter = word[0].uppercaseChar().toString()
    private val textPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        this.textSize = textSize
        textAlign = Paint.Align.LEFT
    }
    private val backgroundPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = backgroundColor ?: Color.parseColor(colors[nextInt(colors.size)])
    }
    private val textRect = Rect()

    override fun draw(canvas: Canvas) {
        val width: Int = bounds.width()
        val height: Int = bounds.height()

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        textPaint.getTextBounds(letter, 0, letter.length, textRect)
        val x: Float = width / 2f - textRect.width() / 2f - textRect.left
        val y: Float = height / 2f + textRect.height() / 2f - textRect.bottom

        canvas.drawText(letter, x, y, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        // Ignore
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // Ignore
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    companion object {
        val colors = listOf(
            "#D32F2F", "#C2185B", "#7B1FA2", "#512DA8",
            "#303F9F", "#1976D2", "#00796B", "#388E3C",
            "#FBC02D", "#FFA000", "#F57C00", "#E64A19"
        )
    }
}