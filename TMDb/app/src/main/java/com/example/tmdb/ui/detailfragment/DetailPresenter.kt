package com.example.tmdb.UI.DetailFragment

import com.example.tmdb.UI.DataModel.DetailDataModel

class DetailPresenter(private var model: DetailContract.Model) :
    DetailContract.Presenter {
    private var view: DetailContract.View? = null

    override fun onAttach(view: DetailContract.View) {
        this.view = view
        model = DetailModel()
        model.setPresenter(this)
        model.fetch(view.getDetailsId(), view.getType())
    }

    override fun onDetach() {
        this.view = null
    }

    override fun fetch(id: Int, type: String) = model.fetch(id, type)

    override fun renderDetails(details: DetailDataModel, type: String) {
        view?.renderDetails(details, type)
    }

}