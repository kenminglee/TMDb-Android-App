package com.example.tmdb.UI.BrowseFragment

import com.example.tmdb.UI.DataModel.SearchAndBrowseDataModel

class BrowsePresenter(private var model: BrowseContract.Model) :
    BrowseContract.Presenter {
    private var view: BrowseContract.View? = null

    init {
        model.setPresenter(this)
    }

    override fun onAttach(view: BrowseContract.View) {
        this.view = view
        model.fetchAllMovies()
    }

    override fun onDetach() {
        this.view = null
    }

    override fun fetchAllMovies() {
        model.fetchAllMovies()
    }

    override fun onMovieClick(movie: SearchAndBrowseDataModel) {
        view?.onMovieClick(movie)
    }

    override fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String) {
        when(type){
            MoviesAdapter.NOW_PLAYING -> {
                view?.updateNowPlayingMovies(movies)
            }
            MoviesAdapter.UPCOMING -> {
                view?.updateUpcomingMovies(movies)
            }
            MoviesAdapter.TOP_RATED -> {
                view?.updateTopRatedMovies(movies)
            }
        }

    }
}