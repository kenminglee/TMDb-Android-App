package com.example.tmdb.UI.BrowseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.R
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import com.example.tmdb.ui.detailfragment.DetailFragment
import com.example.tmdb.ui.searchfragment.ResultsAdapter
import com.example.tmdb.ui.searchfragment.SearchFragment
import kotlinx.android.synthetic.main.fragment_browse.*

class BrowseFragment : Fragment(), BrowseContract.View {
    private lateinit var viewModel: BrowseContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(BrowseViewModel::class.java)
        super.onCreate(savedInstanceState)
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
        viewModel.onAttach(this)

        top_rated_movies.adapter = viewModel.getTopRatedMoviesAdapter()
        upcoming_movies.adapter = viewModel.getUpcomingMoviesAdapter()
        now_playing_movies.adapter = viewModel.getNowPlayingMoviesAdapter()
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
        fun newInstance(): BrowseFragment = BrowseFragment()
    }

}

