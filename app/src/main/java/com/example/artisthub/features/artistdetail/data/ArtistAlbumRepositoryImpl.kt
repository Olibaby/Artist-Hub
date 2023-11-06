package com.example.artisthub.features.artistdetail.data

import com.example.artisthub.core.utils.network.NetworkCall
import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.artistdetail.data.api.ArtistAlbumAPI
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse
import com.example.artisthub.features.artistdetail.repository.ArtistAlbumRepository

class ArtistAlbumRepositoryImpl(private val artistAlbumAPI: ArtistAlbumAPI, private val artistAlbumNetworkCall: NetworkCall): ArtistAlbumRepository {
    override suspend fun getAlbums(query: GetAlbumsQuery): NetworkResult<GetAlbumsResponse> {
        return artistAlbumNetworkCall.makeNetworkCall(query.album,artistAlbumAPI::getAlbums) { it.error == null }
    }
}