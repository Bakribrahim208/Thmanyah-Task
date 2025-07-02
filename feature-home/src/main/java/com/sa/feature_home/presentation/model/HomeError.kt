package com.sa.feature_home.presentation.model

/**
 * Represents different error types that can occur in the UI.
 */
sealed class HomeError {
    /**
     * Network connectivity issues (no internet, timeouts)
     */
    data object Network : HomeError()

    /**
     * Server-side errors (5xx status codes)
     */
    data class Server(val message: String) : HomeError()

    /**
     * Client-side errors (4xx status codes)
     */
    data class Client(val message: String) : HomeError()

    /**
     * When no data is available to display
     */
    data object EmptyData : HomeError()

    /**
     * Unexpected errors
     */
    data class Unknown(val message: String) : HomeError()
}
