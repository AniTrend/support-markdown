package co.anitrend.support.markdown.domain.entities

data class TextFeed(
    val id: Int,
    val text: String,
    val createdAt: String,
    val user: User,
    val replyCount: Int,
    val likes: Int,
    val siteUrl: String
) {
    data class User(
        val id: Int,
        val name: String,
        val avatar: Avatar
    ) {
        data class Avatar(
            val large: String?,
            val medium: String?
        )
    }
}