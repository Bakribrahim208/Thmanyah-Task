package com.sa.thmanyahtast.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

// Extension function on Context to create a singleton DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_preferences")

@Singleton
class ThemePreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val themePreferences = context.dataStore

    private companion object {
        val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    }

    /**
     * Get the current theme mode preference
     */
    val themeMode: Flow<ThemeMode> = themePreferences.data.map { preferences ->
        val themeModeValue = preferences[THEME_MODE_KEY] ?: ThemeMode.SYSTEM.name
        try {
            ThemeMode.valueOf(themeModeValue)
        } catch (e: IllegalArgumentException) {
            ThemeMode.SYSTEM
        }
    }

    /**
     * Update the theme mode preference
     */
    suspend fun updateThemeMode(themeMode: ThemeMode) {
        themePreferences.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.name
        }
    }
}
