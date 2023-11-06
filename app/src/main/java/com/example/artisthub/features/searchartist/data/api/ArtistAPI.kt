package com.example.artisthub.features.searchartist.data.api

import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistAPI {
    @GET("artist/")
    suspend fun searchArtists(@Query("query") query: String): SearchArtistResponse
}