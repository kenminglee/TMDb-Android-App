package com.example.tmdb.Model.Network

import com.google.gson.annotations.SerializedName


// Data Model for TMDB Movie item
data class TmdbData(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("vote_average")
    val vote_average: Double? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("poster_path")
    val poster_path: String? = null,
    @SerializedName("backdrop_path")
    val backdrop_path: String? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null,
    @SerializedName("name")
    val name: String? = null
)


// Data Model for the Response returned from the TMDB Api
data class TmdbDataResponse(
    @SerializedName("results")
    val results: List<TmdbData>? = null
)