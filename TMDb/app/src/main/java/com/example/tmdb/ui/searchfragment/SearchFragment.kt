package com.example.tmdb.ui.searchfragment

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import com.example.tmdb.ui.detailfragment.DetailFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_toolbar.*

class SearchFragment : Fragment(), SearchContract.View{

    private lateinit var presenter: SearchPresenter
    private lateinit var resultsAdapter: ResultsAdapter
    private lateinit var imm: InputMethodManager
    private lateinit var currentQuery: String
    private var inFocus: Boolean = false
    private lateinit var type: String
    private lateinit var cursorAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = SearchPresenter(SearchModel())
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
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        initializeSearchBarSuggestions()
        setSearchBarListeners()
        setSearchBarState()

        resultsAdapter = ResultsAdapter(presenter)
        results_recycler_view.adapter = resultsAdapter

        ivBackArrow.setOnClickListener {
            imm.hideSoftInputFromWindow(view.windowToken,0)
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setSearchBarState(){
        if (inFocus){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            searchBar.requestFocus()
        }else {
            searchBar.clearFocus()
            searchBar.setQuery(currentQuery, false)
        }
    }

    private fun initializeSearchBarSuggestions(){
        searchBar.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        cursorAdapter = SimpleCursorAdapter(context, R.layout.fragment_search_suggestion, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        searchBar.suggestionsAdapter = cursorAdapter
    }

    private fun setSearchBarListeners() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.query(query, ResultsAdapter.MULTI)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                cursor.addRow(arrayOf(0, getString(R.string.theActor, query)))
                cursor.addRow(arrayOf(1, getString(R.string.inMovies, query)))
                cursor.addRow(arrayOf(2, getString(R.string.inTVShow, query)))
                cursorAdapter.changeCursor(cursor)
                return false
            }
        })

        searchBar.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                when(position){
                    0 -> presenter.query(searchBar.query.toString(), ResultsAdapter.PERSON)
                    1 -> presenter.query(searchBar.query.toString(), ResultsAdapter.MOVIE)
                    2 -> presenter.query(searchBar.query.toString(), ResultsAdapter.TV)
                }
                return false
            }
        })
    }


    override fun renderResultsWithPosition(results: List<SearchAndBrowseDataModel>, type: String, position: Int) {
        if(results.isEmpty())
            when(type){
                ResultsAdapter.TV -> search_results.text = getString(R.string.NoResultsFound, currentQuery, "in TV Shows")
                ResultsAdapter.MOVIE -> search_results.text = getString(R.string.NoResultsFound, currentQuery, "in Movies")
                ResultsAdapter.PERSON -> search_results.text = getString(R.string.NoResultsFound, currentQuery, "the Actor")
                else -> {search_results.text = getString(R.string.NoResultsFound, currentQuery, "")}
            }
        else
            when(type){
                ResultsAdapter.TV -> search_results.text = getString(R.string.ShowingResults, results.size, currentQuery, "in TV Shows")
                ResultsAdapter.MOVIE -> search_results.text = getString(R.string.ShowingResults, results.size, currentQuery, "in Movies")
                ResultsAdapter.PERSON -> search_results.text = getString(R.string.ShowingResults, results.size, currentQuery, "the Actor")
                else -> {search_results.text = getString(R.string.ShowingResults, results.size, currentQuery, "")}
            }
        updateRecyclerViewAdapter(results)
        results_recycler_view.scrollToPosition(position)
    }

    private fun updateRecyclerViewAdapter(results: List<SearchAndBrowseDataModel>){
        resultsAdapter.setResults(results)
        resultsAdapter.notifyDataSetChanged()
    }

    override fun onClickResult(info: SearchAndBrowseDataModel) = navigateToNewFragment(DetailFragment.newInstance(info))

    override fun getQuery(): String = currentQuery


    override fun getInFocus(): Boolean = inFocus

    override fun displayQueryingInformation(query: String, type: String) {
        when(type){
            ResultsAdapter.MULTI -> search_results.text = getString(R.string.SearchingFor, query, "")
            ResultsAdapter.PERSON -> search_results.text = getString(R.string.SearchingFor, query, "the Actor")
            ResultsAdapter.MOVIE -> search_results.text = getString(R.string.SearchingFor, query, "in Movies")
            ResultsAdapter.TV -> search_results.text = getString(R.string.SearchingFor, query, "in TV Show")
        }

    }

    override fun updateUIState(query: String, type: String) {
        imm.hideSoftInputFromWindow(view?.windowToken,0)
        currentQuery = query
        this.type = type
        inFocus = false
    }

    override fun getSharedPref(): SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)

    private fun navigateToNewFragment(fragment: Fragment){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun getStringResource(res: Int): String = getString(res)

    companion object {
        fun newInstance(currentQuery: String, type: String, inFocus: Boolean): SearchFragment = SearchFragment().apply {
            this.currentQuery = currentQuery
            this.type = type
            this.inFocus = inFocus
        }
    }
}
