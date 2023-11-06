package com.example.artisthub.features.artistdetail.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artisthub.MainCoroutineRule
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.core.utils.network.handleException
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse
import com.example.artisthub.features.artistdetail.presentation.viewmodel.ArtistAlbumViewModel
import com.example.artisthub.features.artistdetail.repository.ArtistAlbumRepository
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import com.example.artisthub.features.searchartist.presentation.viewmodel.SearchArtistViewModel
import com.example.artisthub.features.searchartist.repository.ArtistRepository
import com.example.artisthub.getOrAwaitValueTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*

@ExperimentalCoroutinesApi
class ArtistAlbumViewModelTest {
    private val artistAlbumRepository = mockk<ArtistAlbumRepository>()
    private val viewModel = ArtistAlbumViewModel(artistAlbumRepository)

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
    fun getAlbumsSuccess(){
        // Set up mock objects
        val query = GetAlbumsQuery("artistId")
        val expectedResponse = GetAlbumsResponse(0, 0, listOf(),null)
        coEvery { artistAlbumRepository.getAlbums(query) } returns NetworkResult.Success(expectedResponse)

        runBlocking {
            // Call the function being tested
            viewModel.getAlbums(query)
        }

        val album = viewModel.getAlbumsResponse.getOrAwaitValueTest()
        // Assert the result
        Assert.assertEquals(expectedResponse, album)
        Assert.assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }

    @Test
    fun getAlbumsFailed() {
        // Set up mock objects
        val query = GetAlbumsQuery("artistId")
        val expectedError = "Failed to search artist"
        coEvery { artistAlbumRepository.getAlbums(query) } returns NetworkResult.Failed(
            GetAlbumsResponse(0, 0, listOf(), expectedError )
        )

        runBlocking {
            // Call the function being tested
            viewModel.getAlbums(query)
        }

        // Assert the result
        Assert.assertEquals(expectedError, viewModel.showError.getOrAwaitValueTest())
        Assert.assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }

    @Test
    fun getAlbumsError() {
        // Set up mock objects
        val query = GetAlbumsQuery("artistId")
        val expectedException = RuntimeException("Network error")
        coEvery { artistAlbumRepository.getAlbums(query) } returns NetworkResult.Error(expectedException)

        runBlocking {
            // Call the function being tested
            viewModel.getAlbums(query)
        }
        // Assert the result
        Assert.assertEquals(
            expectedException.handleException(),
            viewModel.showError.getOrAwaitValueTest()
        )
        Assert.assertEquals(false, viewModel.showLoading.getOrAwaitValueTest())
    }
}
