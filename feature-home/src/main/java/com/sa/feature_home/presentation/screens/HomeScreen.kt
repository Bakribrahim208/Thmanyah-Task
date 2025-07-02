package com.sa.feature_home.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sa.core.presentation.components.EmptyContent
import com.sa.core.presentation.components.ErrorContent
import com.sa.feature_home.presentation.model.HomeIntent
import com.sa.feature_home.presentation.model.HomeUiEffect
import com.sa.feature_home.presentation.ui.SectionContainerItem
import com.sa.feature_home.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Collect the sections paging data from the UI state
    val sectionsPagingItems = uiState.sectionsFlow?.collectAsLazyPagingItems()

    // State for the Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for side effects from the ViewModel
    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message, duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = modifier, snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        // The main UI content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // First check if we have a flow to collect
            if (sectionsPagingItems == null) {
                // If sectionsFlow is null, it means we're still initializing
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            // Check the high-level loading state from UI state
            else if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            // Check if there's an error from the ViewModel
            else if (uiState.error != null) {
                // Show appropriate error UI based on error type
                ErrorContent(
                    error = uiState.error!!,
                    onRetry = { viewModel.processIntent(HomeIntent.Refresh) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            // If no errors, handle the different states from the Paging library
            else {
                when (val refreshState = sectionsPagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    is LoadState.Error -> {
                        // Handle paging-specific errors
                        val errorMessage = refreshState.error.message ?: "Unknown error"
                        val errorType = when (refreshState.error) {
                            is IOException -> "Network error"
                            is HttpException -> {
                                val code = (refreshState.error as HttpException).code()
                                if (code >= 500) "Server error" else "Client error"
                            }

                            else -> "Unknown error"
                        }

                        ErrorContent(
                            errorTitle = errorType,
                            errorMessage = errorMessage,
                            onRetry = { sectionsPagingItems.retry() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is LoadState.NotLoading -> {
                        // If not loading and the item count is zero, show an empty state message
                        if (sectionsPagingItems.itemCount == 0) {
                            EmptyContent(modifier = Modifier.align(Alignment.Center))
                        } else {
                            // If data is loaded successfully, display it in a LazyColumn
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    count = sectionsPagingItems.itemCount,
                                    key = { index -> "section-$index" }) { index ->
                                    val section = sectionsPagingItems[index]
                                    if (section != null) {
                                        SectionContainerItem(section = section)
                                    }
                                }

                                // Handle loading state for appending more pages
                                when (sectionsPagingItems.loadState.append) {
                                    is LoadState.Loading -> {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        }
                                    }

                                    is LoadState.Error -> {
                                        item {
                                            Button(
                                                onClick = { sectionsPagingItems.retry() },
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text("Retry Loading More")
                                            }
                                        }
                                    }

                                    is LoadState.NotLoading -> {
                                        // Do nothing when not loading
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
