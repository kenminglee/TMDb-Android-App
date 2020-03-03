package com.example.tmdb.UI.BrowseFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import com.example.tmdb.ui.detailfragment.DetailContract

interface BrowseContract {
    interface View{
        fun onMovieClick(movie: SearchAndBrowseDataModel)
    }

    interface Presenter {
        fun onViewAttached(view: BrowseContract.View, router: BrowseContract.Router)
        fun onDetach()
        fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String)
    }

    interface Repository {
        fun setInteractor(interactor: Interactor)
        fun fetchAllMovies()
    }

    interface ViewModel{
        fun onAttach(v: View)
        fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String)
//        fun setTopRatedMoviesAdapter(adapter: MoviesAdapter)
//        fun setUpcomingMoviesAdapter(adapter: MoviesAdapter)
//        fun setNowPlayingMoviesAdapter(adapter: MoviesAdapter)
        fun onMovieClick(movie: SearchAndBrowseDataModel)
        fun updateTopRatedMovies( movies: List<SearchAndBrowseDataModel>)
        fun updateUpcomingMovies( movies: List<SearchAndBrowseDataModel>)
        fun updateNowPlayingMovies( movies: List<SearchAndBrowseDataModel>)

        fun getTopRatedMoviesAdapter(): MoviesAdapter
        fun getUpcomingMoviesAdapter(): MoviesAdapter
        fun getNowPlayingMoviesAdapter(): MoviesAdapter
    }

    interface Router{
        fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?)
    }

    interface Interactor {
        fun setPresenter(presenter: DetailContract.Presenter)
        fun onRouterAttached(router: DetailContract.Router)
        fun onRouterDetached()
        fun renderMovies(movies: List<SearchAndBrowseDataModel>, type: String)
    }

    interface Model {
        fun fetchAllMovies()
        fun setViewModel(viewmodel: BrowseContract.ViewModel)
    }
}