package com.sa.feature_search.presentation.viewmodel

import kotlinx.coroutines.flow.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sa.core.presentation.uiModel.SectionError

import com.sa.core.presentation.uiModel.toSectionUiModel
import com.sa.feature_search.domain.usecase.GetHomeSectionsUseCase
import com.sa.feature_search.presentation.model.SearchIntent
import com.sa.feature_search.presentation.model.SearchUIState
import com.sa.feature_search.presentation.model.SearchUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel() {
    // Private MutableStateFlow to hold the UI state.
    private val _uiState = MutableStateFlow(SearchUIState())
    val uiState: StateFlow<SearchUIState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<SearchUiEffect>()
    val effect: SharedFlow<SearchUiEffect> = _effect.asSharedFlow()

    private var searchJob: Job? = null


    /**
     * Processes incoming intents from the UI.
     *
     * @param intent The user action or event.
     */
    fun processIntent(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.Refresh -> loadSections(isRefresh = true, query = _uiState.value.query)
            is SearchIntent.Search -> handleSearchQuery(query = intent.query)
        }
    }

    /**
     * Handles search query with debounce functionality
     * Delays the search by 200ms to avoid excessive API calls
     * @param query The search term entered by the user
     */
    private fun handleSearchQuery(query: String) {
        // Update the query in UI state immediately to reflect user input
        _uiState.update { it.copy(query = query) }

        // Cancel any previous search job if it's still active
        searchJob?.cancel()

        // Create a new search job with debounce functionality
        searchJob = viewModelScope.launch {
            // Only perform search if query is not empty
            if (query.isNotEmpty()) {
                // Delay for 200ms (debounce time)
                delay(200)
                // Perform search
                loadSections(query = query)
            } else {
                // Clear results if query is empty
                _uiState.update {
                    it.copy(
                        sectionsFlow = null, isLoading = false, error = null
                    )
                }
            }
        }
    }

    /**
     * Loads search results based on the provided query.
     *
     * @param isRefresh Whether this is a refresh operation.
     * @param query The search query
     */
    private fun loadSections(isRefresh: Boolean = false, query: String) {
        viewModelScope.launch {
            try {
                if (!isRefresh) {
                    _uiState.update { it.copy(isLoading = true) }
                }

                val sectionsFlow = getHomeSectionsUseCase(query).map { pagingData ->
                    pagingData.map { it.toSectionUiModel() }
                }.cachedIn(viewModelScope)

                _uiState.update {
                    it.copy(
                        sectionsFlow = sectionsFlow, isLoading = false, error = null
                    )
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(
                        isLoading = false, error = SectionError.Network
                    )
                }
            } catch (e: HttpException) {
                val errorType = when (e.code()) {
                    in 400..499 -> SectionError.Client(e.message())
                    in 500..599 -> SectionError.Server(e.message())
                    else -> SectionError.Unknown(e.message())
                }
                _uiState.update {
                    it.copy(
                        isLoading = false, error = errorType
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = SectionError.Unknown(e.message ?: "Unknown error")
                    )
                }
            }
        }
    }
}
