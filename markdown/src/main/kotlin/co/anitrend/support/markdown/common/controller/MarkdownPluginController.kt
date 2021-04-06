package co.anitrend.support.markdown.common.controller

/**
 * Plugin controller contract
 *
 * @since 0.12.0
 */
abstract class MarkdownPluginController {

    /**
     * Regular expression that should be used for the implementing classing
     */
    protected abstract val regex: Regex

    /**
     * Finds all matches for a the given [regex]
     *
     * @param text The text to search in
     * @param startIndex Optional starting index for searching
     */
    fun findAllMatches(text: String, startIndex: Int = 0): Sequence<MatchResult> =
        regex.findAll(text, startIndex)
}