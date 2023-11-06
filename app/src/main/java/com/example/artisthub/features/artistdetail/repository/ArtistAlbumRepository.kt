package com.example.artisthub.features.artistdetail.repository

import com.example.artisthub.core.utils.network.NetworkResult
import com.example.artisthub.features.artistdetail.data.model.album.GetAlbumsQuery
import com.example.artisthub.features.artistdetail.data.model.album.response.GetAlbumsResponse

interface ArtistAlbumRepository {
    suspend fun getAlbums(query: GetAlbumsQuery): NetworkResult<GetAlbumsResponse>
}