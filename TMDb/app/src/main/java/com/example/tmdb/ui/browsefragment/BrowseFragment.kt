package com.example.tmdb.UI.BrowseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.UI.DataModel.SearchAndBrowseDataModel
import com.example.tmdb.UI.DetailFragment.DetailFragment
import com.example.tmdb.UI.SearchFragment.ResultsAdapter
import com.example.tmdb.UI.SearchFragment.SearchFragment
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment : Fragment(),
    BrowseContract.View {

    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var presenter: BrowseContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter =
            BrowsePresenter(BrowseModel())
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        presenter.onAttach(this)
        super.onResume()
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_browse, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchView2.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(hasFocus) navigateToNewFragment(SearchFragment.newInstance("", ResultsAdapter.MULTI, true))
        }

        topRatedMoviesAdapter =
            MoviesAdapter(listOf(), presenter)
        top_rated_movies.adapter = topRatedMoviesAdapter

        upcomingMoviesAdapter =
            MoviesAdapter(listOf(), presenter)
        upcoming_movies.adapter = upcomingMoviesAdapter

        nowPlayingMoviesAdapter =
            MoviesAdapter(listOf(), presenter)
        now_playing_movies.adapter = nowPlayingMoviesAdapter
    }

    override fun updateTopRatedMovies(movies: List<SearchAndBrowseDataModel>) {
        topRatedMoviesAdapter.movies = movies
        topRatedMoviesAdapter.notifyDataSetChanged()
    }

    override fun updateUpcomingMovies(movies: List<SearchAndBrowseDataModel>) {
        upcomingMoviesAdapter.movies = movies
        upcomingMoviesAdapter.notifyDataSetChanged()
    }

    override fun updateNowPlayingMovies(movies: List<SearchAndBrowseDataModel>) {
        nowPlayingMoviesAdapter.movies = movies
        nowPlayingMoviesAdapter.notifyDataSetChanged()
    }

    override fun onMovieClick(movie: SearchAndBrowseDataModel) {
        navigateToNewFragment(DetailFragment.newInstance(movie))
    }

    private fun navigateToNewFragment(fragment: Fragment){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object{
        fun newInstance(): BrowseFragment =
            BrowseFragment()
    }

}

