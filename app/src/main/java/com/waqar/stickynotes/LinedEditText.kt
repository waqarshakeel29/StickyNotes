package com.waqar.stickynotes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.EditText


class LinedEditText(context: Context, attrs: AttributeSet) : EditText(context, attrs) {
    private val mRect: Rect = Rect()
    private val mPaint: Paint = Paint()

    init {

        // define the style of line
        mPaint.style = Paint.Style.STROKE
        // define the color of line
        mPaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        val height = height
        val lHeight = lineHeight
        // the number of line
        var count = height / lHeight
        if (lineCount > count) {
            // for long text with scrolling
            count = lineCount
        }
        val r = mRect
        val paint = mPaint

        // first line
        var baseline = getLineBounds(0, r)

        // draw the remaining lines.
        for (i in 0 until count) {
            canvas.drawLine(
                r.left.toFloat(),
                (baseline+1).toFloat(),
                r.right.toFloat(),
                (baseline+1).toFloat(),
                paint
            )
            // next line
            baseline += lineHeight
        }
        super.onDraw(canvas)
    }
}