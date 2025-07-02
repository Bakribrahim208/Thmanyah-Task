package com.sa.feature_home.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sa.feature_home.data.source.remote.HomeApiService
import com.sa.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class HomeRepositoryImpl @Inject constructor(
    private val apiService: HomeApiService,
) : HomeRepository {

    override fun getHomeSections(): Flow<PagingData<HomeSectionEntity>> {
        // Return the PagingData flow
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                Log.d("HomeRepositoryImpl", "Creating new HomePagingSource")
                HomePagingSource(apiService)
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getHomeSectionsByPage(page: Int): List<HomeSectionEntity> {
        Log.d("HomeRepositoryImpl", "Directly fetching page $page")
        try {
            val response = apiService.getHomeSections(page)
            return response.sections.map { it.toDomain() }
        } catch (e: HttpException) {
            Log.e("HomeRepositoryImpl", "HTTP error fetching page $page: ${e.message()}")
//            throw mapNetworkError(e)
            throw DomainError.NoConnection
        } catch (e: IOException) {
            Log.e("HomeRepositoryImpl", "IO error fetching page $page: ${e.message}")
            throw DomainError.NoConnection
        }
    }
}


