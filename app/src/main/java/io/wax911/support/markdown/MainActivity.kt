package io.wax911.support.markdown

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val bottomDrawerBehavior: BottomSheetBehavior<FrameLayout> by lazy {
        BottomSheetBehavior.from(bottomDrawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        bottomAppBar.also { bar: BottomAppBar ->
            bar.setNavigationOnClickListener {
                bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
            bar.replaceMenu(R.menu.main_menu)
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    override fun onBackPressed() {
        if (bottomDrawerBehavior.state != BottomSheetBehavior.STATE_COLLAPSED)
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        super.onBackPressed()
    }
}
