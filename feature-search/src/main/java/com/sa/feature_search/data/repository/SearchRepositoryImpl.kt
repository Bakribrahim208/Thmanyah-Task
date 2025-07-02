package com.sa.feature_search.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sa.core.data.source.remote.HomeApiService
import com.sa.core.domain.entities.HomeSectionEntity
import com.sa.feature_search.data.source.SearchPagingSource
import com.sa.feature_search.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class SearchRepositoryImpl @Inject constructor(
    private val apiService: HomeApiService,
) : SearchRepository {

    override fun sectionSearch(query: String): Flow<PagingData<HomeSectionEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, enablePlaceholders = false, initialLoadSize = 20
            ), pagingSourceFactory = {
                Log.d("HomeRepositoryImpl", "Creating new SearchRepositoryImpl")
                SearchPagingSource(apiService, query)
            }).flow.flowOn(Dispatchers.IO)
    }
}


