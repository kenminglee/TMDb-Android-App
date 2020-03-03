package com.example.tmdb.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//A retrofit Network Interface for the Api
interface TmdbApi{
    @GET("movie/{type}")
    suspend fun getMovies(@Path("type") type: String): Response<TmdbDataResponse>

    @GET("search/{option}")
    suspend fun query(@Path("option") option: String, @Query("query") q: String?): Response<TmdbDataResponse>

    @GET("{option}/{id}")
    suspend fun getDetails(@Path("option") option: String, @Path("id") id: Int): Response<TmdbSpecific>

}
