package com.example.tmdb.ui.detailfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tmdb.ui.datamodel.DetailDataModel

class DetailPresenter : DetailContract.Presenter{
    private var view: DetailContract.View? = null
    private var interactor: DetailContract.Interactor? = null

    override fun onViewAttached(view: DetailContract.View, router: DetailContract.Router) {
        this.view = view
        interactor = DetailInteractor(view.getDetailsId(), view.getType())
        (interactor as DetailInteractor).setPresenter(this)
        (interactor as DetailInteractor).onRouterAttached(router)
    }

    override fun onDetach() {
        this.view = null
    }

    override fun fetch(id: Int, type: String){ interactor?.fetch(id, type) }

    override fun renderDetails(details: DetailDataModel, type: String) {
        view?.getViewModel()?.renderDetails(details, type)
        view?.renderDetails(details, type)
    }

    override fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?) {
        interactor?.navigateToWebView(fragment, fragmentManager)
    }

}