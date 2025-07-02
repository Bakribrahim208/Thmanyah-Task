package com.sa.feature_search.presentation.model

/**
 * Represents one-time events or side effects that the UI should handle.
 */
sealed class SearchUiEffect {
    /**
     * Effect to show a snackbar or toast message.
     * @param message The message to be displayed.
     */
    data class ShowSnackbar(val message: String) : SearchUiEffect()
}