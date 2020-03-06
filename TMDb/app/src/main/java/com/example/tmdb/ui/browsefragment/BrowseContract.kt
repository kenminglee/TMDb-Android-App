package com.example.tmdb.UI.BrowseFragment

import com.example.tmdb.UI.DataModel.SearchAndBrowseDataModel

interface BrowseContract {
    interface View{
        fun updateTopRatedMovies(movies: List<SearchAndBrowseDataModel>)
        fun updateUpcomingMovies(movies: List<SearchAndBrowseDataModel>)
        fun updateNowPlayingMovies(movies: List<SearchAndBrowseDataModel>)
        fun onMovieClick(movie: SearchAndBrowseDataModel)
    }

    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()
        fun fetchAllMovies()
        fun onMovieClick(movie: SearchAndBrowseDataModel)
        fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String)
    }
    interface Model{
        fun setPresenter(presenter: Presenter)
        fun fetchAllMovies()
    }
}