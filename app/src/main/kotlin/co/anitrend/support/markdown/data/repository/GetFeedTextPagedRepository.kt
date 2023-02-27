package co.anitrend.support.markdown.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.anitrend.support.markdown.data.FeedDataSource
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.flow.Flow

internal class GetFeedTextPagedRepository(
    private val client: ApolloClient
) {
    operator fun invoke(query: TextFeedQuery): Flow<PagingData<TextFeed>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = query.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FeedDataSource(client, query)
            }
        )
        return pager.flow
    }
}