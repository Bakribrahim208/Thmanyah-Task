package com.sa.feature_home.data.paging

import androidx.paging.PagingSource
import com.sa.core.data.model.HomeResponse
import com.sa.core.data.model.Section
import com.sa.feature_home.data.source.HomePagingSource
import com.sa.core.data.source.remote.HomeApiService
import com.sa.core.domain.entities.ContentType
import com.sa.core.domain.entities.HomeSectionEntity
import com.sa.core.domain.entities.SectionType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomePagingSourceTest {

    private lateinit var api: HomeApiService
    private lateinit var pagingSource: HomePagingSource

    @Before
    fun setUp() {
        api = mockk()
        pagingSource = HomePagingSource(api)
    }

    @Test
    fun `load returns page when API returns valid section list`() = runTest {
        val mockSections = listOf(
            Section(
                content = null,
                contentType = "type",
                name = "Popular",
                order = 1,
                type = "category"
            )
        )
        val response = HomeResponse(
            pagination = null,
            sections = mockSections
        )
        coEvery { api.getHomeSections(1) } returns response

        val expectedResult = PagingSource.LoadResult.Page(
            data = listOf(
                HomeSectionEntity(
                    order = 1,
                    name = "Popular",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            ),
            prevKey = null,
            nextKey = 2
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun `load returns empty page when API returns null section list`() = runTest {
        val response = HomeResponse(
            pagination = null,
            sections = emptyList()
        )
        coEvery { api.getHomeSections(1) } returns response

        val expectedResult = PagingSource.LoadResult.Page<Int, HomeSectionEntity>(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, result)
    }

    @Test
    fun `load returns error when API throws IOException`() = runTest {
        coEvery { api.getHomeSections(1) } throws IOException("No internet")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertTrue(result.throwable is IOException)
    }

    @Test
    fun `load returns error when API throws HttpException`() = runTest {
        val exception = mockk<HttpException>()
        coEvery { api.getHomeSections(1) } throws exception

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertTrue(result.throwable is HttpException)
    }
}
