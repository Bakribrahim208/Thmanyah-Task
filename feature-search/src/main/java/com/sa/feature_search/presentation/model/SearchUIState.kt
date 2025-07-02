package com.sa.feature_search.presentation.model

import androidx.paging.PagingData
import com.sa.core.presentation.uiModel.SectionError
import com.sa.core.presentation.uiModel.SectionUiModel
import kotlinx.coroutines.flow.Flow


/**
 * Represents the state of the Search screen.
 *
 * @param query The current search query text
 * @param sectionsFlow The flow of paginated list of search results to display.
 * @param isLoading Indicates if the initial load is in progress.
 * @param error Specific error type if any error occurred.
 */

data class SearchUIState(
    val query: String = "",
    val sectionsFlow: Flow<PagingData<SectionUiModel>>? = null,
    val isLoading: Boolean = true,
    val error: SectionError? = null
)