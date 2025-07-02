package com.sa.feature_search.presentation.model
/**
 * Represents user actions or events from the UI.
 */
sealed class SearchIntent {
    /**
     * Intent to refresh the list of home sections.
     */
    object Refresh : SearchIntent()

    /**
     * Intent to perform a search with the given query.
     * @param query The search term entered by the user
     */
    data class Search(val query: String) : SearchIntent()
}
