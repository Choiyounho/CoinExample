package com.soten.coinexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatTextView

class Badge constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    var badgeColor = Color.LTGRAY
        set(value) {
            field = value
            invalidate()
        }
//    var strokeColor = Color.BLACK

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val verticalPadding = dip(4f)
        val horizontalPadding = dip(8f)
        setPadding(
            horizontalPadding,
            verticalPadding,
            horizontalPadding,
            verticalPadding
        )

        paint.strokeWidth = 8f

        setTextColor(Color.BLACK)
        textSize = 12f
        typeface = Typeface.DEFAULT_BOLD
        background = null
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            0f,0f,
            paint.apply { color = badgeColor }
        )


        super.onDraw(canvas)
    }
}

@Px
fun View.dip(dipValue: Float) = context.dip(dipValue)

@Px
fun Context.dip(dipValue: Float) = (dipValue * resources.displayMetrics.density).toInt()