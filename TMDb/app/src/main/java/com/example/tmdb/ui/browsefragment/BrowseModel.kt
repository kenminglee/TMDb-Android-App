package com.example.tmdb.UI.BrowseFragment

import kotlinx.coroutines.CoroutineScope
import com.example.tmdb.model.network.ApiFactory
import com.example.tmdb.model.network.TmdbApi
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import com.example.tmdb.ui.searchfragment.ResultsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class BrowseModel : BrowseContract.Model{
    private lateinit var viewModel: BrowseContract.ViewModel
    private lateinit var service: TmdbApi

    override fun fetchAllMovies(){
        service = ApiFactory().getRetrofitClient()
        getMovies(MoviesAdapter.TOP_RATED)
        getMovies(MoviesAdapter.UPCOMING)
        getMovies(MoviesAdapter.NOW_PLAYING)
    }

    private fun getMovies(type: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getMovies(type)
            withContext(Dispatchers.Main){
                try{
                    val temp = response.body()?.results?.map { Tmdb ->
                        SearchAndBrowseDataModel(
                            Tmdb.id?: 0,
                            Tmdb.mediaType?: ResultsAdapter.MOVIE,
                            Tmdb.name?: Tmdb.title?: "",
                            Tmdb.vote_average?: -1.0,
                            Tmdb.poster_path ?: Tmdb.backdrop_path ?: Tmdb.profilePath ?: ""
                        )
                    }
                    if (response.isSuccessful) viewModel.renderMovies(temp?: listOf(), type)
                } catch (e: Exception) {e.printStackTrace()}
            }
        }
    }

    override fun setViewModel(viewmodel: BrowseContract.ViewModel) {
        this.viewModel = viewmodel
    }

}