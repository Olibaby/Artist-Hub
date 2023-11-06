package com.example.artisthub.features.searchartist.data.model.response

data class SearchArtistResponse(
    val artists: List<Artist>?,
    val count: Int?,
    val created: String?,
    val offset: Int?,
    val error: String?
)