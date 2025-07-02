package com.sa.feature_home.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sa.feature_home.data.source.remote.HomeApiService
import com.sa.feature_home.domain.entities.HomeSectionEntity
import retrofit2.HttpException
import java.io.IOException
import kotlin.collections.mapNotNull
import kotlin.let

class HomePagingSource(
    private val api: HomeApiService
) : PagingSource<Int, HomeSectionEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeSectionEntity> {
        val page = params.key ?: 1

        return try {
            val response = api.getHomeSections(page = page)
            val sections = response.sections?.mapNotNull { it?.toDomain() } ?: emptyList()

            LoadResult.Page(
                data = sections,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (sections.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(Throwable("Unexpected error: ${e.message}"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, HomeSectionEntity>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                anchor
            )?.nextKey?.minus(1)
        }
    }
}
