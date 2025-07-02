package com.sa.core.presentation.uiModel

/**
 * Represents different error types that can occur in the UI.
 */
sealed class SectionError {
    /**
     * Network connectivity issues (no internet, timeouts)
     */
    data object Network : SectionError()

    /**
     * Server-side errors (5xx status codes)
     */
    data class Server(val message: String) : SectionError()

    /**
     * Client-side errors (4xx status codes)
     */
    data class Client(val message: String) : SectionError()

    /**
     * When no data is available to display
     */
    data object EmptyData : SectionError()

    /**
     * Unexpected errors
     */
    data class Unknown(val message: String) : SectionError()
}