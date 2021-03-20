package co.anitrend.support.markdown.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.anitrend.support.markdown.domain.entities.TextFeed
import co.anitrend.support.markdown.domain.model.TextFeedQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
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
            val activity = requireNotNull(it.asTextActivity)
            TextFeed(
                id = activity.id,
                text = activity.text.orEmpty(),
                createdAt = activity.createdAt.getDateTime(),
                user = activity.user!!.let { result ->
                    TextFeed.User(
                        id = result.fragments.user.id,
                        name = result.fragments.user.name,
                        avatar = TextFeed.User.Avatar(
                            large = result.fragments.user.avatar?.large,
                            medium = result.fragments.user.avatar?.medium
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

    /**
     * Provide a [Key] used for the initial [load] for the next [PagingSource] due to invalidation
     * of this [PagingSource]. The [Key] is provided to [load] via [LoadParams.key].
     *
     * The [Key] returned by this method should cause [load] to load enough items to
     * fill the viewport around the last accessed position, allowing the next generation to
     * transparently animate in. The last accessed position can be retrieved via
     * [state.anchorPosition][PagingState.anchorPosition], which is typically
     * the top-most or bottom-most item in the viewport due to access being triggered by binding
     * items as they scroll into view.
     *
     * For example, if items are loaded based on integer position keys, you can return
     * [state.anchorPosition][PagingState.anchorPosition].
     *
     * Alternately, if items contain a key used to load, get the key from the item in the page at
     * index [state.anchorPosition][PagingState.anchorPosition].
     *
     * @param state [PagingState] of the currently fetched data, which includes the most recently
     * accessed position in the list via [PagingState.anchorPosition].
     *
     * @return [Key] passed to [load] after invalidation used for initial load of the next
     * generation. The [Key] returned by [getRefreshKey] should load pages centered around
     * user's current viewport. If the correct [Key] cannot be determined, `null` can be returned
     * to allow [load] decide what default key to use.
     */
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
            asHtml = Input.optional(query.asHtml),
            hasRepliesOrTypeText = Input.optional(query.hasRepliesOrTypeText),
            userId = Input.optional(query.userId)
        )

        return try {
            val request = client.query(filter)
            val response = request.await()
            if (response.hasErrors()) {
                val message = response.errors?.firstOrNull()?.message
                LoadResult.Error(
                    Throwable(message)
                )
            }
            else {
                val page = requireNotNull(response.data?.page)
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