package co.anitrend.support.markdown.sample.feed.viewmodel.contract

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.domain.interactor.GetTextFeedPaged
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal abstract class AbstractFeedViewModel : ViewModel() {
    val query: MutableStateFlow<TextFeedQuery?> = MutableStateFlow(null)

    abstract val result: Flow<PagingData<TextFeed>>

    protected abstract val interactor: GetTextFeedPaged

    abstract suspend operator fun invoke(textFeedQuery: TextFeedQuery)
}