package com.example.artisthub.features.artistdetail.data.model.album.response

import com.google.gson.annotations.SerializedName

data class GetAlbumsResponse(
    @SerializedName("release-count")
    val releaseCount: Int?,
    @SerializedName("release-offset")
    val releaseOffset: Int?,
    val releases: List<Release>?,
    val error: String?
)