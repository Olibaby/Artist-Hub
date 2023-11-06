package com.example.artisthub.features.searchartist.data.model.response

import com.google.gson.annotations.SerializedName

data class EndArea(
    val id: String?,
    @SerializedName("life-span")
    val lifeSpan: LifeSpanXX?,
    val name: String?,
    @SerializedName("sort-name")
    val sortName: String?,
    val type: String?,
    @SerializedName("type-id")
    val typeId: String?
)