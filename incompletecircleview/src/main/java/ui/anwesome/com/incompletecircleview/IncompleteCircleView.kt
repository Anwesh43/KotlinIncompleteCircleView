package ui.anwesome.com.incompletecircleview

/**
 * Created by anweshmishra on 01/04/18.
 */
import android.app.Activity
import android.graphics.*
import android.content.Context
import android.view.*

class IncompleteCircleView(ctx: Context) : View(ctx) {
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer : IncompleteCircleRenderer = IncompleteCircleRenderer(this)
    override fun onDraw(canvas: Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class IncompleteCircleState(var prevScale: Float = 0f, var dir: Float = 0f, var j: Int = 0) {
        val scales: Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb: (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating(startcb: () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class IncompleteCircleAnimator(var view: View, var animated: Boolean = false) {
        fun animate(updatecb: () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex: Exception) {

                }
            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class IncompleteCircle(var i : Int, val state : IncompleteCircleState = IncompleteCircleState()) {
        fun draw (canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            val r : Float = Math.min(w, h)/10
            paint.style = Paint.Style.STROKE
            canvas.save()
            canvas.translate(w/2, h/2)
            canvas.rotate(90f * state.scales[1])
            paint.color = Color.parseColor("#E74856")
            paint.strokeWidth = r/6
            paint.strokeCap = Paint.Cap.ROUND
            for(i in 0..3) {
                canvas.save()
                canvas.rotate(90f * i)
                canvas.translate(w/2 * ((1 - state.scales[0]) + state.scales[2]), 0f)
                canvas.drawArc(RectF(-r, -r, r, r), -30f , 60f, false, paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class IncompleteCircleRenderer(var view : IncompleteCircleView) {
        val incompleteCircle : IncompleteCircle = IncompleteCircle(0)
        val animator : IncompleteCircleAnimator = IncompleteCircleAnimator(view)
        fun render (canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            incompleteCircle.draw(canvas, paint)
            animator.animate {
                incompleteCircle.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            incompleteCircle.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : IncompleteCircleView {
            val view : IncompleteCircleView = IncompleteCircleView(activity)
            val size : Point = DimensionsUtil.getDimensions(activity)
            activity.addContentView(view, ViewGroup.LayoutParams(size.x, size.x))
            return view
        }
    }
}