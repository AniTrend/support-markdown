package co.anitrend.support.markdown.domain.interactor

import co.anitrend.support.markdown.domain.common.PagingDataState
import co.anitrend.support.markdown.domain.model.TextFeedQuery

abstract class GetTextFeedPaged {
    abstract operator fun invoke(
        query: TextFeedQuery
    ): PagingDataState
}