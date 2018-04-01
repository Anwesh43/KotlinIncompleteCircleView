package ui.anwesome.com.kotlinincompletecircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.incompletecircleview.IncompleteCircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IncompleteCircleView.create(this)
    }
}
