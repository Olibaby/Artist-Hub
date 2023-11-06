package com.example.artisthub.features.searchartist.data.model.response

import com.google.gson.annotations.SerializedName

data class Aliase(
    @SerializedName("begin-date")
    val beginDate: String?,
    @SerializedName("end-date")
    val endDate: String?,
    val locale: String?,
    val name: String?,
    val primary: String?,
    @SerializedName("sort-name")
    val sortName: String?,
    val type: String?
)