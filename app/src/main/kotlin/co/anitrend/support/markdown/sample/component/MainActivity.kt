package co.anitrend.support.markdown.sample.component

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import co.anitrend.support.markdown.core.AbstractActivity
import co.anitrend.support.markdown.sample.R
import co.anitrend.support.markdown.sample.databinding.ActivityMainBinding
import co.anitrend.support.markdown.sample.feed.FeedFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AbstractActivity<ActivityMainBinding>() {

    private val bottomDrawerBehavior by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetBehavior.from<FrameLayout>(
            requireBinding().bottomDrawer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(requireBinding().root)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        requireBinding().bottomAppBar.also { bar: BottomAppBar ->
            bar.setNavigationOnClickListener {
                bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
            bar.replaceMenu(R.menu.main_menu)
        }
        updateUI()
    }

    private fun updateUI() {
        supportFragmentManager.commit {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            replace(
                R.id.contentFrame,
                FeedFragment::class.java,
                Bundle.EMPTY,
                FeedFragment::class.simpleName
            )
        }
    }
}
