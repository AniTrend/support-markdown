package co.anitrend.support.markdown.sample.feed.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.domain.interactor.GetTextFeedPaged
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import co.anitrend.support.markdown.sample.feed.viewmodel.contract.AbstractFeedViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class FeedViewModel(
    override val interactor: GetTextFeedPaged
) : AbstractFeedViewModel() {

    override val result = MutableStateFlow<PagingData<TextFeed>>(PagingData.empty())

    init {
        viewModelScope.launch {
            query.filterNotNull()
                .onEach {
                    invoke(it)
                }
                .catch { cause: Throwable ->
                    Log.e(javaClass.simpleName, "Error collecting flow", cause)
                }
                .collect()
        }
    }

    override suspend fun invoke(textFeedQuery: TextFeedQuery) {
        val state = interactor(textFeedQuery)
        val pagingData = state.data.cachedIn(viewModelScope)
        result.emitAll(pagingData)
    }
}
