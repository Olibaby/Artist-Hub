package com.example.artisthub.features.artistdetail.data.api

import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistAlbumAPI {
    @GET("release/")
    suspend fun getAlbums(@Query("artist") artist: String): GetAlbumsResponse
}