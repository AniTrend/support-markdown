package co.anitrend.support.markdown.data.usecase

import co.anitrend.support.markdown.data.repository.GetFeedTextPagedRepository
import co.anitrend.support.markdown.domain.common.PagingDataState
import co.anitrend.support.markdown.domain.interactor.GetTextFeedPaged
import co.anitrend.support.markdown.domain.model.TextFeedQuery

internal class TextFeedUseCase(
    private val repository: GetFeedTextPagedRepository
) : GetTextFeedPaged() {
    override fun invoke(
        query: TextFeedQuery
    ) = PagingDataState(
        data = repository(query)
    )
}