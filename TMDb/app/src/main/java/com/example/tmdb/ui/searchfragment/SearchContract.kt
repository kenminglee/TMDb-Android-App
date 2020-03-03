package com.example.tmdb.ui.searchfragment

import android.content.SharedPreferences
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel

interface SearchContract {
    interface View{
        fun renderResultsWithPosition(results: List<SearchAndBrowseDataModel>, type: String, position: Int)
        fun onClickResult(info: SearchAndBrowseDataModel)
        fun getQuery(): String
        fun getInFocus(): Boolean
        fun displayQueryingInformation(query: String, type: String)
        fun updateUIState(query: String, type: String)
        fun getSharedPref(): SharedPreferences?
        fun getStringResource(res: Int): String
    }

    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()
        fun onClickResult(info: SearchAndBrowseDataModel, position: Int)
        fun query(keyword: String, type: String)
        fun findPositionAndRenderResults(results: List<SearchAndBrowseDataModel>, type: String)
    }

    interface Model{
        fun setPresenter(presenter: Presenter)
        fun queryMulti(keyword: String)
        fun querySpecific(keyword: String, type: String)
    }
}