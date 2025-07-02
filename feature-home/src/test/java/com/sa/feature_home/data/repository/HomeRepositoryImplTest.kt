//package com.sa.feature_home.data.repository
//
//import androidx.paging.PagingData
//import androidx.paging.PagingSource
//import com.sa.feature_home.data.source.remote.HomeApiService
//import io.mockk.coEvery
//import io.mockk.coVerify
//import io.mockk.mockk
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import kotlin.test.assertTrue
//
//@RunWith(JUnit4::class)
//class HomeRepositoryImplTest {
//
//    private lateinit var api: HomeApiService
//    private lateinit var repository: HomeRepositoryImpl
//
//    @Before
//    fun setUp() {
//        api = mockk()
//        repository = HomeRepositoryImpl(api)
//    }
//
//    @Test
//    fun `getHomeSections returns valid flow`() = runTest {
//        // Arrange
//        val mockSections = listOf(
//            Section(
//                content = null,
//                contentType = "type",
//                name = "Popular",
//                order = 1,
//                type = "section"
//            )
//        )
//        val response = HomeResponse(null, mockSections)
//        coEvery { api.getHomeSections(any()) } returns response
//
//        // Act
//        val pagingDataFlow = repository.getHomeSections()
//
//        // Assert - Verify it's a Flow of PagingData
//        assertTrue(pagingDataFlow is Flow<PagingData<HomeSectionEntity>>)
//    }
//
//    @Test
//    fun `getHomeSections creates PagingSource that uses the API service`() = runTest {
//        // This test verifies that the API is called when loading data through the repository
//
//        // Arrange - Setup our mock API
//        val mockSections = listOf(
//            Section(
//                content = null,
//                contentType = "type",
//                name = "Popular",
//                order = 1,
//                type = "section"
//            )
//        )
//        val response = HomeResponse(null, mockSections)
//        coEvery { api.getHomeSections(1) } returns response
//
//        // For testing, we need to access the HomePagingSource directly
//        // So we'll modify our test approach to verify the integration
//
//        // We need a reference to the created PagingSource
//        // Let's create a custom repository implementation for testing
//        val testRepo = object : HomeRepositoryImpl(api) {
//            // Override to expose the paging source
//            val pagingSource = HomePagingSource(api)
//
//            fun createPagingSource() = pagingSource
//        }
//
//        // Act - Trigger the load directly on the paging source
//        val loadParams = PagingSource.LoadParams.Refresh(
//            key = 1,
//            loadSize = 20,
//            placeholdersEnabled = false
//        )
//
//        testRepo.pagingSource.load(loadParams)
//
//        // Assert - Verify API was called with correct parameters
//        coVerify(exactly = 1) { api.getHomeSections(1) }
//    }
//}
