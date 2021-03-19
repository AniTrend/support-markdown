package co.anitrend.support.markdown.sample.feed

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import co.anitrend.support.markdown.core.AbstractFragment
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import co.anitrend.support.markdown.sample.R
import co.anitrend.support.markdown.sample.databinding.FragmentFeedListBinding
import co.anitrend.support.markdown.sample.feed.adapter.FeedAdapter
import co.anitrend.support.markdown.sample.feed.viewmodel.contract.AbstractFeedViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FeedFragment(
    private val adapter: FeedAdapter
) : AbstractFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    private val viewModel by viewModel<AbstractFeedViewModel>()

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
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    requireBinding().feedSwipeRefresh.isRefreshing = false
                    requireBinding().feedRecycler.scrollToPosition(0)
                }
        }
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