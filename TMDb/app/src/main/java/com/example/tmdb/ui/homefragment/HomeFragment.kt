package com.example.tmdb.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.UI.BrowseFragment.BrowseFragment
import com.example.tmdb.ui.searchfragment.ResultsAdapter
import com.example.tmdb.ui.searchfragment.SearchFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                navigateToNewFragment(SearchFragment.newInstance(query, ResultsAdapter.MULTI, false))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        ijustwanttobrowse.setOnClickListener {
            navigateToNewFragment(BrowseFragment.newInstance())
        }
    }

    override fun onResume() {
        searchView.setQuery("", false)
        searchView.clearFocus()
        super.onResume()
    }

    private fun navigateToNewFragment(fragment: Fragment){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }
    companion object{
        fun newInstance(): HomeFragment = HomeFragment()
    }

}
