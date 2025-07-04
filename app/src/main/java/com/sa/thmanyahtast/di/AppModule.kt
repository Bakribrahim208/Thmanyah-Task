package com.sa.thmanyahtast.di

import android.content.Context
import com.sa.thmanyahtast.data.preferences.ThemePreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideThemePreferenceDataStore(@ApplicationContext context: Context): ThemePreferenceDataStore {
        return ThemePreferenceDataStore(context)
    }
}
