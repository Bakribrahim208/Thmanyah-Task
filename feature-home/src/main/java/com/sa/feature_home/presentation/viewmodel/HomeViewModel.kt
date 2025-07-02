package com.sa.feature_home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sa.feature_home.domain.usecase.GetHomeSectionsUseCase
import com.sa.core.presentation.uiModel.SectionError
import com.sa.feature_home.presentation.model.HomeIntent
import com.sa.feature_home.presentation.model.HomeUiEffect
import com.sa.feature_home.presentation.model.HomeUiState
import com.sa.core.presentation.uiModel.toSectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase
) : ViewModel() {
    // Private MutableStateFlow to hold the UI state.
    private val _uiState = MutableStateFlow(HomeUiState())

    // Public immutable StateFlow for the UI to observe.
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // SharedFlow for one-time UI effects.
    private val _effect = MutableSharedFlow<HomeUiEffect>()
    val effect: SharedFlow<HomeUiEffect> = _effect.asSharedFlow()

    init {
        // Trigger the initial data load when the ViewModel is created.
        processIntent(HomeIntent.LoadHomeSections)
    }

    /**
     * Processes incoming intents from the UI.
     *
     * @param intent The user action or event.
     */
    fun processIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadHomeSections -> loadSections()
            is HomeIntent.Refresh -> loadSections(isRefresh = true)
        }
    }

    /**
     * Fetches the home sections from the use case and updates the UI state.
     *
     * @param isRefresh Whether this is a refresh request or initial load
     */
    private fun loadSections(isRefresh: Boolean = false) {
        viewModelScope.launch {
            // Start with loading state
            if (!isRefresh) {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }

            try {
                // Create the sections flow
                val sectionsFlow = getHomeSectionsUseCase.invoke()
                    .map { pagingData ->
                        pagingData.map { entity ->
                            entity.toSectionUiModel()
                        }
                    }
                    .cachedIn(viewModelScope)

                // Update the UI state with the flow
                _uiState.update {
                    it.copy(
                        sectionsFlow = sectionsFlow,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                // Handle any exceptions
                val errorType = when (e) {
                    is IOException -> SectionError.Network
                    is HttpException -> {
                        when (e.code()) {
                            in 500..599 -> SectionError.Server(e.message())
                            in 400..499 -> SectionError.Client(e.message())
                            else -> SectionError.Unknown(e.message())
                        }
                    }
                    else -> SectionError.Unknown(e.message ?: "An unknown error occurred")
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = errorType
                    )
                }

                // Emit a UI effect to show a snackbar
                _effect.emit(HomeUiEffect.ShowSnackbar(
                    message = e.message ?: "An unknown error occurred"
                ))
            }
        }
    }
}
