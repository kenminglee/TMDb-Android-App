package com.example.tmdb.model.network

import com.google.gson.annotations.SerializedName

data class TmdbSpecific(
    @SerializedName("vote_count")
    val vote_count: Int? = null,
    @SerializedName("imdb_id")
    val imdb_id: String? = null,
    @SerializedName("biography")
    val biography: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("poster_path")
    val poster_path: String? = null,
    @SerializedName("backdrop_path")
    val backdrop_path: String? = null,
    @SerializedName("profile_path")
    val profilePath: String? = null
)