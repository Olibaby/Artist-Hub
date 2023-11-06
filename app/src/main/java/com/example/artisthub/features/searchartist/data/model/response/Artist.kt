package com.example.artisthub.features.searchartist.data.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Artist(
    val aliases: List<Aliase>?,
    val area: Area?,
    @SerializedName("begin-area")
    val beginArea: BeginArea?,
    val country: String?,
    val disambiguation: String?,
    @SerializedName("end-area")
    val endArea: EndArea?,
    val id: String?,
    val isnis: List<String>?,
    @SerializedName("life-span")
    val lifeSpan: LifeSpanXXX?,
    val name: String?,
    val score: Int?,
    @SerializedName("sort-name")
    val sortName: String?,
    val tags: List<Tag>?,
    val type: String?,
    @SerializedName("type-id")
    val typeId: String?
): Serializable