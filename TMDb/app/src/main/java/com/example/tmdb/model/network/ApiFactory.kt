package com.example.tmdb.Model.Network

import com.example.tmdb.Model.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory{

    //Creating Auth Interceptor to add api_key query in front of all the requests.
    private fun getAuthInterceptor(): Interceptor{
        return Interceptor {chain->
            val newUrl = chain.request().url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstants.tmdbApiKey)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }

    fun getRetrofitClient(): TmdbApi {
        //OkhttpClient for building http request url
        val tmdbClient = OkHttpClient().newBuilder()
            .addInterceptor(getAuthInterceptor())
            .build()

        fun retrofit() : Retrofit = Retrofit.Builder()
            .client(tmdbClient)
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit().create(TmdbApi::class.java)
    }




}