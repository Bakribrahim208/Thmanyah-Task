package com.sa.feature_search.di

import com.sa.feature_search.data.repository.SearchRepositoryImpl
import com.sa.feature_search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepoModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository
}
