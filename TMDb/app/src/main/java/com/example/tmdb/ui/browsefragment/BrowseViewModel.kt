package com.example.tmdb.UI.BrowseFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel

class       BrowseViewModel (application: Application) : AndroidViewModel(application), BrowseContract.ViewModel {
    val topRatedMoviesAdapter: MutableLiveData<MoviesAdapter> = MutableLiveData(MoviesAdapter(listOf(), this))
    val upcomingMoviesAdapter: MutableLiveData<MoviesAdapter> = MutableLiveData(MoviesAdapter(listOf(), this))
    val nowPlayingMoviesAdapter: MutableLiveData<MoviesAdapter> = MutableLiveData(MoviesAdapter(listOf(), this))
    private lateinit var view: BrowseContract.View
    private var model: BrowseContract.Model

    init {
        model = BrowseModel()
        model.setViewModel(this)
    }

    override fun updateTopRatedMovies(movies: List<SearchAndBrowseDataModel>) {
        topRatedMoviesAdapter.value?.movies = movies
        (topRatedMoviesAdapter.value as MoviesAdapter).notifyDataSetChanged()
    }

    override fun updateUpcomingMovies(movies: List<SearchAndBrowseDataModel>) {
        upcomingMoviesAdapter.value?.movies = movies
        (upcomingMoviesAdapter.value as MoviesAdapter).notifyDataSetChanged()
    }

    override fun updateNowPlayingMovies(movies: List<SearchAndBrowseDataModel>) {
        nowPlayingMoviesAdapter.value?.movies = movies
        (nowPlayingMoviesAdapter.value as MoviesAdapter).notifyDataSetChanged()
    }

    override fun onAttach(v: BrowseContract.View) {
        this.view = v
        model.fetchAllMovies()
    }

    override fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String) {
        when(type){
            MoviesAdapter.NOW_PLAYING -> {
                updateNowPlayingMovies(movies)
            }
            MoviesAdapter.UPCOMING -> {
                updateUpcomingMovies(movies)
            }
            MoviesAdapter.TOP_RATED -> {
                updateTopRatedMovies(movies)
            }
        }
    }

    override fun onMovieClick(movie: SearchAndBrowseDataModel) {
        view.onMovieClick(movie)
    }

    override fun getTopRatedMoviesAdapter(): MoviesAdapter = topRatedMoviesAdapter.value?: MoviesAdapter(
        listOf(), this)
    override fun getUpcomingMoviesAdapter(): MoviesAdapter = upcomingMoviesAdapter.value?: MoviesAdapter(
        listOf(), this)
    override fun getNowPlayingMoviesAdapter(): MoviesAdapter = nowPlayingMoviesAdapter.value?: MoviesAdapter(
        listOf(), this)
}