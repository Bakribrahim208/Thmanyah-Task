package com.sa.feature_search.domain.usecase

import androidx.paging.PagingData
import com.sa.core.domain.entities.HomeSectionEntity
import com.sa.feature_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeSectionsUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(param: String): Flow<PagingData<HomeSectionEntity>> =
        repository.sectionSearch(param)

}