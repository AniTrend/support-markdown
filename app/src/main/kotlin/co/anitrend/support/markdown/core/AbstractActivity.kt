package co.anitrend.support.markdown.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.activityRetainedScope
import org.koin.core.component.KoinScopeComponent

abstract class AbstractActivity<B: ViewBinding> : AppCompatActivity(),
    CoroutineScope by MainScope(), AndroidScopeComponent {

    override val scope by activityRetainedScope()

    protected var binding: B? = null

    protected fun requireBinding(): B =
        requireNotNull(binding) {
            "Binding has not yet been initialized"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory(scope)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}