package com.sa.feature_search.domain.repository

import androidx.paging.PagingData
import com.sa.core.domain.entities.HomeSectionEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun sectionSearch(query: String): Flow<PagingData<HomeSectionEntity>>

}