package ui.anwesome.com.incompletecircleview

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.view.Display

/**
 * Created by anweshmishra on 01/04/18.
 */

class DimensionsUtil {
    companion object {
        fun getDimensions(activity : Activity) : Point {
            val point : Point = Point()
            val displayManager : DisplayManager =  activity.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            val display : Display = displayManager.getDisplay(0)
            display.getRealSize(point)
            return point
        }
    }
}