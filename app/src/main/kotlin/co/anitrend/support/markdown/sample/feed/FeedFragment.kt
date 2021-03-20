package co.anitrend.support.markdown.sample.feed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.anitrend.support.markdown.core.AbstractFragment
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import co.anitrend.support.markdown.sample.R
import co.anitrend.support.markdown.sample.databinding.FragmentFeedListBinding
import co.anitrend.support.markdown.sample.feed.adapter.FeedAdapter
import co.anitrend.support.markdown.sample.feed.viewmodel.contract.AbstractFeedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FeedFragment(
    private val adapter: FeedAdapter
) : AbstractFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    private val viewModel by viewModel<AbstractFeedViewModel>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        /**
         * Callback for handling the [OnBackPressedDispatcher.onBackPressed] event.
         */
        override fun handleOnBackPressed() {
            val default = TextFeedQuery(
                hasRepliesOrTypeText = true,
                asHtml = false
            )
            if (viewModel.query.value != default) {
                viewModel.query.tryEmit(default)
                lifecycleScope.launch {
                    viewModel(default)
                }
            }
            else
                activity?.finish()
        }
    }

    private fun setUpRecyclerAdapter() {
        requireBinding().feedRecycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        requireBinding().feedRecycler.adapter = adapter
    }

    /**
     * Stub to trigger the loading of data, by default this is only called
     *
     * This is called when the fragment reaches it's [onResume] state
     */
    override fun onFetchDataInitialize() {
        if (adapter.itemCount < 1)
            viewModel.query.value = TextFeedQuery(
                hasRepliesOrTypeText = true,
                asHtml = false
            )
    }

    /**
     * Additional initialization to be done in this method, this method will be called in
     * [androidx.fragment.app.FragmentActivity.onCreate].
     *
     * @param savedInstanceState
     */
    override fun initializeComponents(savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .collect {
                    when (val state = it.refresh) {
                        is LoadState.NotLoading -> {
                            requireBinding().feedSwipeRefresh.isRefreshing = false
                            requireBinding().feedRecycler.scrollToPosition(0)
                        }
                        is LoadState.Loading -> {
                            requireBinding().feedSwipeRefresh.isRefreshing = true
                        }
                        is LoadState.Error -> {
                            Snackbar.make(
                                requireBinding().root,
                                state.error.message.orEmpty(),
                                Snackbar.LENGTH_INDEFINITE
                            ).apply {
                                setAction("Retry") {
                                    adapter.retry()
                                    dismiss()
                                }
                            }
                            requireBinding().feedSwipeRefresh.isRefreshing = false
                        }
                    }
                }
        }
        lifecycleScope.launchWhenResumed {
            adapter.clickState.filterNotNull()
                .onEach { feed ->
                    val query = TextFeedQuery(
                        hasRepliesOrTypeText = true,
                        asHtml = false,
                        userId = feed.user.id
                    )
                    viewModel.query.value = query
                    viewModel(query)
                }
                .catch { cause ->
                    Log.e(javaClass.simpleName, "Error collecting flow", cause)
                }
                .collect()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    /**
     * Invoke view model observer to watch for changes, this will be called
     * called in [onViewCreated]
     */
    override suspend fun setUpViewModelObserver() {
        viewModel.result.collectLatest {
            adapter.submitData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedListBinding.bind(view)
        requireBinding().feedSwipeRefresh.isEnabled = true
        requireBinding().feedSwipeRefresh.setOnRefreshListener {
            adapter.refresh()
        }
        setUpRecyclerAdapter()
    }

    override fun onDestroy() {
        binding?.feedSwipeRefresh?.setOnRefreshListener(null)
        super.onDestroy()
    }
}