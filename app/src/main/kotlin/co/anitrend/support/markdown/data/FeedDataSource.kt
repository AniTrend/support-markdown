package co.anitrend.support.markdown.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.anitrend.support.markdown.domain.entities.FeedListQuery
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import java.text.SimpleDateFormat
import java.util.*

internal class FeedDataSource(
    private val client: ApolloClient,
    private val query: TextFeedQuery
): PagingSource<Int, TextFeed>() {

    private fun Int.getDateTime(): String {
        return try {
            val formatter = SimpleDateFormat("dd-M-yyyy hh:mm", Locale.getDefault())
            val date = Date(toLong() * 1000)
            formatter.format(date)
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun convert(activities: List<FeedListQuery.Activity?>?): List<TextFeed> {
        val result = activities?.filterNotNull()?.map {
            val activity = requireNotNull(it.onTextActivity)
            TextFeed(
                id = activity.id,
                text = activity.text.orEmpty(),
                createdAt = activity.createdAt.getDateTime(),
                user = activity.user!!.let { result ->
                    TextFeed.User(
                        id = result.user.id,
                        name = result.user.name,
                        avatar = TextFeed.User.Avatar(
                            large = result.user.avatar?.onUserAvatar?.large,
                            medium = result.user.avatar?.onUserAvatar?.medium
                        ),
                    )
                },
                replyCount = activity.replyCount,
                likes = activity.likeCount,
                siteUrl = activity.siteUrl!!,
            )
        }

        return result.orEmpty()
    }

    private fun createNextKey(resultCount: Int, pageInfo: FeedListQuery.PageInfo?): Int? {
        return if (resultCount == 0)
            null
        else pageInfo?.currentPage?.plus(1)
    }

    private fun createPreviousKey(position: Int): Int? {
        return if (position == STARTING_PAGE) null
        else position.minus(1)
    }

    override fun getRefreshKey(state: PagingState<Int, TextFeed>): Int {
        return STARTING_PAGE
    }

    /**
     * Loading API for [PagingSource].
     *
     * Implement this method to trigger your async load (e.g. from database or network).
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TextFeed> {
        val position = params.key ?: STARTING_PAGE
        val filter = FeedListQuery(
            page = position,
            perPage = query.pageSize,
            asHtml = Optional.present(query.asHtml),
            hasRepliesOrTypeText = Optional.present(query.hasRepliesOrTypeText),
            userId = Optional.presentIfNotNull(query.userId)
        )

        return try {
            val request = client.query(filter)
            val response = request.execute()
            if (response.hasErrors()) {
                val message = response.errors?.firstOrNull()?.message
                LoadResult.Error(
                    Throwable(message)
                )
            }
            else {
                val page = requireNotNull(response.data?.Page)
                val textFeeds = convert(page.activities)

                val nextKey = createNextKey(textFeeds.size, page.pageInfo)
                val previousKey = createPreviousKey(position)

                LoadResult.Page(
                    data = textFeeds,
                    prevKey = previousKey,
                    nextKey = nextKey
                )
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val STARTING_PAGE = 1
    }
}