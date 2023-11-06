package com.example.artisthub.features.artistdetail.data.model.album.response

import com.google.gson.annotations.SerializedName

data class Release(
    val asin: String?,
    val barcode: String?,
    @SerializedName("cover-art-archive")
    val coverArtArchive: CoverArtArchive?,
    val disambiguation: String?,
    val id: String?,
    val packaging: String?,
    @SerializedName("packaging-id")
    val packagingId: String?,
    val quality: String?,
    val status: String?,
    @SerializedName("status-id")
    val statusId: String?,
    @SerializedName("text-representation")
    val textRepresentation: TextRepresentation?,
    val title: String?,
    val score: Int?,
    val date: String?,
    val country: String?,
    @SerializedName("track-count")
    val trackCount: Int?
)