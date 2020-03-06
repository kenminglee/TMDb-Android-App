package com.example.tmdb.UI.DetailFragment

import com.example.tmdb.UI.DataModel.DetailDataModel

interface DetailContract {
    interface View{
        fun renderDetails(details: DetailDataModel, type: String)
        fun getDetailsId(): Int
        fun getType(): String
    }

    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()
        fun fetch(id: Int, type: String)
        fun renderDetails(details: DetailDataModel, type: String)
    }

    interface Model{
        fun setPresenter(presenter: Presenter)
        fun fetch(id: Int, type: String)
    }
}