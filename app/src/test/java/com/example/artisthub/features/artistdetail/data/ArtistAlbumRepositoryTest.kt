package com.example.artisthub.features.artistdetail.data

import com.example.artisthub.core.utils.network.NetworkCall
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.artistdetail.data.api.ArtistAlbumAPI
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse
import com.example.artisthub.features.searchartist.data.ArtistRepositoryImpl
import com.example.artisthub.features.searchartist.data.api.ArtistAPI
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ArtistAlbumRepositoryTest {

    private lateinit var artistAlbumAPI: ArtistAlbumAPI
    private lateinit var artistAlbumNetworkCall: NetworkCall
    private lateinit var artistAlbumRepositoryImpl: ArtistAlbumRepositoryImpl

    @Before
    fun setUp() {
        artistAlbumAPI = mockk()
        artistAlbumNetworkCall = mockk()
        artistAlbumRepositoryImpl = ArtistAlbumRepositoryImpl(artistAlbumAPI, artistAlbumNetworkCall)
    }

    @Test
    fun getAlbumsSuccess(){
        // given
        val query = GetAlbumsQuery("artistId")
        val expectedResponse = GetAlbumsResponse(0, 0, listOf(),null)
        coEvery { artistAlbumNetworkCall.makeNetworkCall(query.album, artistAlbumAPI::getAlbums, any()) } returns NetworkResult.Success(expectedResponse)

        lateinit var actualResult: NetworkResult<GetAlbumsResponse>
        runBlocking {
            // when
            actualResult = artistAlbumRepositoryImpl.getAlbums(query)
        }
        // then
        Assert.assertEquals(expectedResponse, actualResult.data)
    }

    @Test
    fun getAlbumsFailed(){
        // given
        val query = GetAlbumsQuery("artistId")
        val errorMessage = "Error occurred"
        val expectedResponse = GetAlbumsResponse(0, 0, listOf(),errorMessage)
        coEvery { artistAlbumNetworkCall.makeNetworkCall(query.album, artistAlbumAPI::getAlbums, any()) } returns NetworkResult.Failed(expectedResponse)

        lateinit var actualResult: NetworkResult<GetAlbumsResponse>
        runBlocking {
            // when
            actualResult = artistAlbumRepositoryImpl.getAlbums(query)
        }
        // then
        Assert.assertEquals(expectedResponse, actualResult.data)
        Assert.assertEquals(errorMessage, actualResult.data?.error)
    }

    @Test
    fun getAlbumsError(){
        // given
        val query = GetAlbumsQuery("artistId")
        val expectedException = RuntimeException("Something went wrong")
        coEvery { artistAlbumNetworkCall.makeNetworkCall(query.album, artistAlbumAPI::getAlbums, any()) } returns NetworkResult.Error(expectedException)

        lateinit var actualResult: NetworkResult<GetAlbumsResponse>
        runBlocking {
            // when
            actualResult = artistAlbumRepositoryImpl.getAlbums(query)
        }
        // then
        Assert.assertEquals(expectedException, actualResult.exception)
    }
}