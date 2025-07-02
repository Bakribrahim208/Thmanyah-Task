package com.sa.feature_home.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.sa.core.domain.error.DomainError
import com.sa.feature_home.domain.entities.ContentType
import com.sa.feature_home.domain.entities.HomeSectionEntity
import com.sa.feature_home.domain.entities.SectionType
import com.sa.feature_home.domain.repository.HomeRepository
import com.sa.feature_home.domain.usecase.GetHomeSectionsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class GetHomeSectionsUseCaseTest {

    private lateinit var repository: HomeRepository
    private lateinit var useCase: GetHomeSectionsUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetHomeSectionsUseCase(repository)
    }

    @Test
    fun `should emit paged list of home sections`() = runTest {
        val expectedList = listOf(
            HomeSectionEntity(
                order = 1,
                name = "Popular",
                content = emptyList(),
                contentType = ContentType.UNKNOWN,
                type = SectionType.UNKNOWN
            )
        )
        val expectedPaging = PagingData.from(expectedList)

        coEvery { repository.getHomeSections() } returns flowOf(expectedPaging)

        useCase().test {
            // Just verify we received a PagingData item (can't compare content directly)
            awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `should emit empty paging list when repository returns empty list`() =
        runTest {
            val emptyPaging: PagingData<HomeSectionEntity> = PagingData.from(emptyList())

            coEvery { repository.getHomeSections() } returns flowOf(emptyPaging)

            useCase().test {
                // Just verify we received a PagingData item
                awaitItem()
                awaitComplete()
            }
        }

    @Test
    fun `getPage should return successful result with list of home sections`() = runTest {
        val expectedList = listOf(
            HomeSectionEntity(
                order = 1,
                name = "Popular",
                content = emptyList(),
                contentType = ContentType.UNKNOWN,
                type = SectionType.UNKNOWN
            )
        )

        coEvery { repository.getHomeSectionsByPage(1) } returns expectedList

        val result = useCase.getPage(1)
        assertTrue(result.isSuccess)
        assertEquals(expectedList, result.getOrNull())
    }

    @Test
    fun `getPage should return failure when repository throws exception`() = runTest {
        val exception = DomainError.NoConnection

        coEvery { repository.getHomeSectionsByPage(1) } throws exception

        val result = useCase.getPage(1)
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }


    @Test
    fun `should emit failure result with EmptyResult when no items are available`() = runTest {
        coEvery { repository.getHomeSectionsByPage(any()) } throws DomainError.EmptyResult

        val result = useCase.getPage(1)
        assertTrue(result.isFailure)
        assertIs<DomainError.EmptyResult>(result.exceptionOrNull())
    }

    @Test
    fun `should emit failure result with Unknown error when unspecified error occurs`() = runTest {
        coEvery { repository.getHomeSectionsByPage(any()) } throws DomainError.Unknown("Unexpected error")

        val result = useCase.getPage(1)
        assertTrue(result.isFailure)
        assertIs<DomainError.Unknown>(result.exceptionOrNull())
        assertEquals("Unexpected error", (result.exceptionOrNull() as DomainError.Unknown).message)
    }

    @Test
    fun `should emit multiple paging data items when repository loads more data`() = runTest {
        val page1 = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 1,
                    name = "Top Popular",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )
        val page2 = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 2,
                    name = "Latest",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )

        coEvery { repository.getHomeSections() } returns flowOf(page1, page2)

        useCase().test {
            // First page emission
            awaitItem()

            // Second page emission
            awaitItem()

            // Flow completes after all items
            awaitComplete()
        }
    }

    @Test
    fun `should emit fresh successful result when repository refreshes`() = runTest {
        val initialData = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 1,
                    name = "Popular",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )
        val refreshedData = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 1,
                    name = "Popular",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                ),
                HomeSectionEntity(
                    order = 2,
                    name = "Latest",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )

        coEvery { repository.getHomeSections() } returns flowOf(initialData, refreshedData)

        useCase().test {
            // Initial data emission
            awaitItem()

            // Refreshed data emission
            awaitItem()

            awaitComplete()
        }
    }

    @Test
    fun `should emit success then error when error occurs after successful page load`() =
        runTest {
            val firstPage = PagingData.from(
                listOf(
                    HomeSectionEntity(
                        order = 2,
                        name = "Latest",
                        content = emptyList(),
                        contentType = ContentType.UNKNOWN,
                        type = SectionType.UNKNOWN
                    )
                )
            )

            coEvery { repository.getHomeSections() } returns flow {
                emit(firstPage)
                throw DomainError.NoConnection
            }

            useCase().test {
                // First successful emission
                awaitItem()

                // Then we should get an error
                val error = awaitError()
                assertIs<DomainError.NoConnection>(error)
            }
        }

    @Test
    fun `should handle intermittent errors during pagination`() = runTest {
        val firstPage = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 1,
                    name = "Top Popular",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )
        val secondPage = PagingData.from(
            listOf(
                HomeSectionEntity(
                    order = 2,
                    name = "Latest",
                    content = emptyList(),
                    contentType = ContentType.UNKNOWN,
                    type = SectionType.UNKNOWN
                )
            )
        )

        var attemptCount = 0
        coEvery { repository.getHomeSections() } answers {
            flow {
                emit(firstPage)
                attemptCount++
                if (attemptCount == 1) {
                    throw DomainError.NoConnection
                } else {
                    emit(secondPage)
                }
            }
        }

        // First attempt with error
        useCase().test {
            // First page emission
            awaitItem()

            // Error on second page load
            val error = awaitError()
            assertIs<DomainError.NoConnection>(error)
        }

        // Second attempt succeeds
        useCase().test {
            // First page emission
            awaitItem()

            // Second page successfully loaded
            awaitItem()

            awaitComplete()
        }
    }
}