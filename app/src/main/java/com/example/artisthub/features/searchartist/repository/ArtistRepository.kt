package com.example.artisthub.features.searchartist.repository

import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse

interface ArtistRepository {
    suspend fun searchArtist(query: SearchArtistQuery): NetworkResult<SearchArtistResponse>
}

