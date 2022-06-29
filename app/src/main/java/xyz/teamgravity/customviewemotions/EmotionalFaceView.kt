package xyz.teamgravity.customviewemotions

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.os.bundleOf

class EmotionalFaceView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4F

        private const val SUPER_STATE = "superState"
        private const val HAPPINESS_STATE = "happinessState"

        const val HAPPY = 0
        const val SAD = 1
    }

    private val paint = Paint()
    private val mouthPath = Path()

    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var size = 0

    var happinessState = HAPPY
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

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

    override fun onSaveInstanceState(): Parcelable {
        return bundleOf(
            SUPER_STATE to super.onSaveInstanceState(),
            HAPPINESS_STATE to happinessState
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState = state

        if (superState is Bundle) {
            happinessState = superState.getInt(HAPPINESS_STATE, HAPPY)
            superState = superState.getParcelable(SUPER_STATE)
        }

        super.onRestoreInstanceState(superState)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.EmotionalFaceView, 0, 0).use { array ->
            happinessState = array.getInt(R.styleable.EmotionalFaceView_state, HAPPY)
            faceColor = array.getColor(R.styleable.EmotionalFaceView_faceColor, DEFAULT_FACE_COLOR)
            eyesColor = array.getColor(R.styleable.EmotionalFaceView_eyesColor, DEFAULT_EYES_COLOR)
            mouthColor = array.getColor(R.styleable.EmotionalFaceView_mouthColor, DEFAULT_MOUTH_COLOR)
            borderColor = array.getColor(R.styleable.EmotionalFaceView_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = array.getDimension(R.styleable.EmotionalFaceView_borderWidth, DEFAULT_BORDER_WIDTH)
        }
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
        mouthPath.reset()
        mouthPath.moveTo(size * 0.22F, size * 0.7F)

        when (happinessState) {
            HAPPY -> {
                mouthPath.quadTo(size * 0.5F, size * 0.8F, size * 0.78F, size * 0.7F)
                mouthPath.quadTo(size * 0.5F, size * 0.9F, size * 0.22F, size * 0.7F)
            }
            SAD -> {
                mouthPath.quadTo(size * 0.5F, size * 0.5F, size * 0.78F, size * 0.7F)
                mouthPath.quadTo(size * 0.5F, size * 0.6F, size * 0.22F, size * 0.7F)
            }
        }

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(mouthPath, paint)
    }
}