package com.example.artisthub.features.searchartist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.artisthub.core.utils.network.NetworkCall
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.searchartist.data.api.ArtistAPI
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import com.example.artisthub.features.searchartist.repository.ArtistRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class ArtistRepositoryTest {

    private lateinit var artistAPI: ArtistAPI
    private lateinit var artistNetworkCall: NetworkCall
    private lateinit var artistRepositoryImpl: ArtistRepositoryImpl

    @Before
    fun setUp() {
        artistAPI = mockk()
        artistNetworkCall = mockk()
        artistRepositoryImpl = ArtistRepositoryImpl(artistAPI, artistNetworkCall)
    }

    @Test
    fun searchArtistSuccess(){
        // given
        val query = SearchArtistQuery("artistName")
        val expectedResponse = SearchArtistResponse(listOf(), 0, "2023-03-16", 0, null)
        coEvery { artistNetworkCall.makeNetworkCall(query.artist, artistAPI::searchArtists, any()) } returns NetworkResult.Success(expectedResponse)

        lateinit var actualResult: NetworkResult<SearchArtistResponse>
        runBlocking {
            // when
             actualResult = artistRepositoryImpl.searchArtist(query)
        }
        // then
        assertEquals(expectedResponse, actualResult.data)
    }

    @Test
    fun searchArtistFailed(){
        // given
        val query = SearchArtistQuery("artistName")
        val errorMessage = "Error occurred"
        val expectedResponse = SearchArtistResponse(listOf(), 0, "2023-03-16", 0, errorMessage)
        coEvery { artistNetworkCall.makeNetworkCall(query.artist, artistAPI::searchArtists, any()) } returns NetworkResult.Failed(expectedResponse)

        lateinit var actualResult: NetworkResult<SearchArtistResponse>
        runBlocking {
            // when
             actualResult = artistRepositoryImpl.searchArtist(query)
        }
            // then
            assertEquals(expectedResponse, actualResult.data)
            assertEquals(errorMessage, actualResult.data?.error)
    }

    @Test
    fun searchArtistError(){
        // given
        val query = SearchArtistQuery("artistName")
        val expectedException = RuntimeException("Something went wrong")
        coEvery { artistNetworkCall.makeNetworkCall(query.artist, artistAPI::searchArtists, any()) } returns NetworkResult.Error(expectedException)

        lateinit var actualResult: NetworkResult<SearchArtistResponse>
        runBlocking {
            // when
             actualResult = artistRepositoryImpl.searchArtist(query)
        }
        // then
        assertEquals(expectedException, actualResult.exception)
    }
}