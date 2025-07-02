package com.sa.feature_home.domain.usecase

import androidx.paging.PagingData
import com.sa.core.domain.error.DomainError
import com.sa.feature_home.domain.entities.HomeSectionEntity
import com.sa.feature_home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeSectionsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    operator fun invoke(): Flow<PagingData<HomeSectionEntity>> =
        repository.getHomeSections()
    // New function to get a specific page
    suspend fun getPage(page: Int): Result<List<HomeSectionEntity>> {
        return try {
            val sections = repository.getHomeSectionsByPage(page)
            Result.success(sections)
        } catch (e: DomainError) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}