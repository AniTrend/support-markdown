package co.anitrend.support.markdown.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.scope.fragmentScope
import org.koin.core.component.KoinScopeComponent

abstract class AbstractFragment<B: ViewBinding>(@LayoutRes layoutId: Int) : Fragment(layoutId),
    CoroutineScope by MainScope(), KoinScopeComponent {

    override val scope by fragmentScope()

    protected var binding: B? = null

    protected fun requireBinding(): B =
        requireNotNull(binding) {
            "Binding has not yet been initialized"
        }

    private var viewModelObserverJob: Job? = null

    /**
     * Stub to trigger the loading of data, by default this is only called
     *
     * This is called when the fragment reaches it's [onResume] state
     */
    abstract fun onFetchDataInitialize()

    /**
     * Additional initialization to be done in this method, this method will be called in
     * [androidx.fragment.app.FragmentActivity.onCreate].
     *
     * @param savedInstanceState
     */
    abstract fun initializeComponents(savedInstanceState: Bundle?)

    /**
     * Invoke view model observer to watch for changes, this will be called
     * called in [onViewCreated]
     */
    abstract suspend fun setUpViewModelObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            onFetchDataInitialize()
        }
        initializeComponents(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserverJob = lifecycleScope.launch {
            setUpViewModelObserver()
        }
    }

    override fun onDestroyView() {
        viewModelObserverJob?.cancel()
        super.onDestroyView()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}