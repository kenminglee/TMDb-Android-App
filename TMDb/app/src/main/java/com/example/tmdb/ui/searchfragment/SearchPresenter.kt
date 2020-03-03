package com.example.tmdb.ui.searchfragment

import android.content.SharedPreferences
import com.example.tmdb.R
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel

class SearchPresenter(private var model: SearchContract.Model) : SearchContract.Presenter{
    private var view: SearchContract.View? = null
    private var sharedPreferences: SharedPreferences? = null

    override fun onAttach(view: SearchContract.View) {
        this.view = view
        model = SearchModel()
        model.setPresenter(this)
        sharedPreferences = view.getSharedPref()
        if(view.getQuery().isNotEmpty())
            query(view.getQuery(), ResultsAdapter.MULTI)
    }

    override fun onDetach() {
        view = null
    }

    override fun onClickResult(info: SearchAndBrowseDataModel, position: Int) {
        saveCurrentPosition(position)
        view?.onClickResult(info)
    }

    override fun query(keyword: String, type: String){
        view?.displayQueryingInformation(keyword, type)
        view?.updateUIState(keyword, type)
        if(type == ResultsAdapter.MULTI) model.queryMulti(keyword) else model.querySpecific(keyword, type)
    }

    override fun findPositionAndRenderResults(results: List<SearchAndBrowseDataModel>, type: String){
        view?.renderResultsWithPosition(results, type, retrieveCurrentPosition())
    }

    private fun saveCurrentPosition(pos: Int){
        with (sharedPreferences?.edit()) {
            this?.putInt(view?.getStringResource(R.string.positionKey), pos)
            this?.apply()
        }
    }

    private fun retrieveCurrentPosition(default: Int = 0) : Int = sharedPreferences?.getInt(view?.getStringResource(R.string.positionKey), default)?:0
}