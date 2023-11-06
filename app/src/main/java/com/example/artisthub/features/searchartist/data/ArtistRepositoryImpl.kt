package com.example.artisthub.features.searchartist.data

import com.example.artisthub.core.utils.network.NetworkCall
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.searchartist.data.api.ArtistAPI
import com.example.artisthub.features.searchartist.data.model.SearchArtistQuery
import com.example.artisthub.features.searchartist.data.model.response.SearchArtistResponse
import com.example.artisthub.features.searchartist.repository.ArtistRepository


class ArtistRepositoryImpl(private val artistAPI: ArtistAPI, private val artistNetworkCall: NetworkCall): ArtistRepository {
    override suspend fun searchArtist(query: SearchArtistQuery): NetworkResult<SearchArtistResponse> {
            return artistNetworkCall.makeNetworkCall(query.artist, artistAPI::searchArtists, {it.error == null})
    }
}