package com.sa.feature_home.presentation.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


/**
 * Represents the state of the Home screen.
 *
 * @param sectionsFlow The flow of paginated list of home sections to display.
 * @param isLoading Indicates if the initial load is in progress.
 * @param error Specific error type if any error occurred.
 */

data class HomeUiState(
    val sectionsFlow: Flow<PagingData<SectionUiModel>>? = null,
    val isLoading: Boolean = true,
    val error: HomeError? = null
)