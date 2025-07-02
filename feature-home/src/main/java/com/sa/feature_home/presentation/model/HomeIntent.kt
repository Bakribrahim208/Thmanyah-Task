package com.sa.feature_home.presentation.model
/**
 * Represents user actions or events from the UI.
 */
sealed class HomeIntent {
    /**
     * Intent to load the home sections. This is triggered when the screen is first created.
     */
    object LoadHomeSections : HomeIntent()

    /**
     * Intent to refresh the list of home sections.
     */
    object Refresh : HomeIntent()
}
