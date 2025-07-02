package com.sa.feature_search.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sa.core.presentation.components.EmptyContent
import com.sa.core.presentation.components.ErrorContent
import com.sa.core.presentation.components.SectionContainerItem
import com.sa.feature_search.presentation.model.SearchIntent
import com.sa.feature_search.presentation.model.SearchUiEffect
import com.sa.feature_search.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier, viewModel: SearchViewModel = hiltViewModel()
) {
    // Collect the UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Collect the sections paging data from the UI state
    val sectionsPagingItems = uiState.sectionsFlow?.collectAsLazyPagingItems()

    // State for the Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Focus manager to handle keyboard actions
    val focusManager = LocalFocusManager.current

    // Listen for side effects from the ViewModel
    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SearchUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message, duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            // Search field with debounce functionality
            OutlinedTextField(
                value = uiState.query,
                onValueChange = { query ->
                    viewModel.processIntent(SearchIntent.Search(query))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                label = { Text("Search") },
                placeholder = { Text("Enter search term...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (uiState.query.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.processIntent(SearchIntent.Search(""))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Hide keyboard when search is pressed
                        focusManager.clearFocus()
                    }
                )
            )
        }
    ) { paddingValues ->
        // The main UI content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // First check if we have a flow to collect
            if (sectionsPagingItems == null) {
                // If sectionsFlow is null and there's no query, show empty search state
                if (uiState.query.isEmpty()) {
                    EmptyContent(
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (uiState.isLoading) {
                    // Otherwise show loading indicator if we're searching
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
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
                    onRetry = { viewModel.processIntent(SearchIntent.Refresh) },
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
                            is HttpException -> "Server error"
                            else -> "Unknown error"
                        }

                        ErrorContent(
                            errorTitle = errorType,
                            errorMessage = errorMessage,
                            onRetry = { sectionsPagingItems.refresh() },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is LoadState.NotLoading -> {
                        // Show empty state when there are no items
                        if (sectionsPagingItems.itemCount == 0) {
                            EmptyContent(
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            // Show content when data is available
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(
                                    count = sectionsPagingItems.itemCount,
                                ) { index ->
                                    val item = sectionsPagingItems[index]
                                    if (item != null) {
                                        SectionContainerItem(
                                            section = item,
                                        )
                                    }
                                }

                                // Show loading footer while loading more items
                                item {
                                    when (sectionsPagingItems.loadState.append) {
                                        is LoadState.Loading -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        }

                                        is LoadState.Error -> {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Button(onClick = { sectionsPagingItems.retry() }) {
                                                    Text("Retry")
                                                }
                                            }
                                        }

                                        is LoadState.NotLoading -> { /* No footer when not loading */
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
}
