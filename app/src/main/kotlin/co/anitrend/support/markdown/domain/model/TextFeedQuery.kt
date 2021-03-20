package co.anitrend.support.markdown.domain.model

data class TextFeedQuery(
    val hasRepliesOrTypeText: Boolean,
    val asHtml: Boolean,
    val pageSize: Int = 20,
    val userId: Int? = null
)
