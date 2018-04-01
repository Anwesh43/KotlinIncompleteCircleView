package ui.anwesome.com.incompletecircleview

/**
 * Created by anweshmishra on 01/04/18.
 */
import android.graphics.*
import android.content.Context
import android.view.*

class IncompleteCircleView (ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}