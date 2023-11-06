package com.example.artisthub.features.searchartist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artisthub.MainCoroutineRule
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.core.utils.network.handleException
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import com.example.artisthub.features.searchartist.presentation.viewmodel.SearchArtistViewModel
import com.example.artisthub.features.searchartist.repository.ArtistRepository
import com.example.artisthub.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchArtistViewModelTest {
    private val artistRepository = mockk<ArtistRepository>()
    private val viewModel = SearchArtistViewModel(artistRepository)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel.showError.observeForever {}
    }

    @After
    fun teardown() {
        viewModel.showError.removeObserver {}
    }

    @Test
    fun `searchArtistSuccess`(){
        // Set up mock objects
        val query = SearchArtistQuery("artistName")
        val expectedResponse = SearchArtistResponse(listOf(), 0, "2023-03-16", 0, null)
        coEvery { artistRepository.searchArtist(query) } returns NetworkResult.Success(expectedResponse)

        runBlocking {
            // Call the function being tested
            viewModel.searchArtist(query)
        }

        val artist = viewModel.searchArtistResponse.getOrAwaitValueTest()
        // Assert the result
        assertEquals(expectedResponse, artist)
        assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }

    @Test
    fun `searchArtistFailed`() {
        // Set up mock objects
        val query = SearchArtistQuery("artistName")
        val expectedError = "Failed to search artist"
        coEvery { artistRepository.searchArtist(query) } returns NetworkResult.Failed(
            SearchArtistResponse(listOf(), 0, "2023-03-16", 0, expectedError ))

        runBlocking {
            // Call the function being tested
            viewModel.searchArtist(query)
        }

        // Assert the result
        assertEquals(expectedError, viewModel.showError.getOrAwaitValueTest())
        assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }

    @Test
    fun `searchArtistError`() {
        // Set up mock objects
        val query = SearchArtistQuery("artistName")
        val expectedException = RuntimeException("Network error")
        coEvery { artistRepository.searchArtist(query) } returns NetworkResult.Error(expectedException)

        runBlocking {
            // Call the function being tested
            viewModel.searchArtist(query)
        }
        // Assert the result
        assertEquals(expectedException.handleException(), viewModel.showError.getOrAwaitValueTest())
        assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }
}
