package com.sa.thmanyahtast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sa.thmanyahtast.data.preferences.ThemeMode
import com.sa.thmanyahtast.data.preferences.ThemePreferenceDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreferenceDataStore: ThemePreferenceDataStore
) : ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        // Load the saved theme mode preference when the ViewModel is created
        viewModelScope.launch {
            themePreferenceDataStore.themeMode.collect { savedThemeMode ->
                _themeMode.value = savedThemeMode
            }
        }
    }

    /**
     * Toggle between light, dark, and system modes
     */
    fun cycleThemeMode() {
        val newThemeMode = when (_themeMode.value) {
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.SYSTEM
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
        updateThemeMode(newThemeMode)
    }

    /**
     * Update the theme mode to a specific value
     */
    fun updateThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            themePreferenceDataStore.updateThemeMode(themeMode)
            _themeMode.value = themeMode
        }
    }
}
