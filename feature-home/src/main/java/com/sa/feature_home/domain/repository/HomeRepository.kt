package com.sa.feature_home.domain.repository

import androidx.paging.PagingData
import com.sa.core.domain.entities.HomeSectionEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getHomeSections(): Flow<PagingData<HomeSectionEntity>>

    // New function to load a specific page directly
    suspend fun getHomeSectionsByPage(page: Int): List<HomeSectionEntity>
}