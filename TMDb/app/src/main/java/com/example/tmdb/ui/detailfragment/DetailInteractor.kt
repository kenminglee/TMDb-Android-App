package com.example.tmdb.ui.detailfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tmdb.ui.datamodel.DetailDataModel

class DetailInteractor(details: Int, type: String): DetailContract.Interactor {
    private var router: DetailContract.Router? = null
    private var presenter: DetailContract.Presenter? = null
    private var repo: DetailContract.Repository? = null

    init{
        repo = DetailRepository()
        (repo as DetailRepository).setInteractor(this)
        (repo as DetailRepository).fetch(details, type)
    }
    override fun setPresenter(presenter: DetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun onRouterAttached(router: DetailContract.Router) {
        this.router = router
    }

    override fun onRouterDetached() {
        this.router = null
    }

    override fun fetch(id: Int, type: String) {
        repo?.fetch(id, type)
    }

    override fun renderDetails(details: DetailDataModel, type: String) {
        presenter?.renderDetails(details, type)
    }

    override fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?) {
        router?.navigateToWebView(fragment, fragmentManager)
    }
}