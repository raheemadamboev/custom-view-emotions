package xyz.teamgravity.customviewemotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class EmotionalFaceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mouthPath = Path()

    private var faceColor = Color.YELLOW
    private var eyesColor = Color.BLACK
    private var mouthColor = Color.BLACK
    private var borderColor = Color.BLACK
    private var borderWidth = 4.0F
    private var size = 320

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = measuredWidth.coerceAtMost(measuredHeight)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas) // keep any drawing from the parent side

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        val radius = size / 2F

        paint.color = faceColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(size / 2F, size / 2F, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(size / 2F, size / 2F, radius, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        val leftEyeRect = RectF(size * 0.32F, size * 0.23F, size * 0.43F, size * 0.5F)
        canvas.drawOval(leftEyeRect, paint)

        val rightEyesRect = RectF(size * 0.57F, size * 0.23F, size * 0.68F, size * 0.5F)
        canvas.drawOval(rightEyesRect, paint)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.moveTo(size * 0.22F, size * 0.7F)
        mouthPath.quadTo(size * 0.5F, size * 0.8F, size * 0.78F, size * 0.7F)
        mouthPath.quadTo(size * 0.5F, size * 0.9F, size * 0.22F, size * 0.7F)

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(mouthPath, paint)
    }
}