package com.example.tmdb.ui.detailfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tmdb.ui.datamodel.DetailDataModel

interface DetailContract {
    interface View{
        fun getDetailsId(): Int
        fun getType(): String
        fun getViewModel(): ViewModel
        fun renderDetails(details: DetailDataModel, type: String)
    }

    interface ViewModel{
        fun renderDetails(details: DetailDataModel, type: String)
        fun getPresenter(): Presenter
    }

    interface Presenter {
        fun onViewAttached(view: View, router: Router)
        fun onDetach()
        fun fetch(id: Int, type: String)
        fun renderDetails(details: DetailDataModel, type: String)
        fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?)
    }

    interface Router{
        fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?)
    }

    interface Interactor{
        fun setPresenter(presenter: Presenter)
        fun onRouterAttached(router: Router)
        fun onRouterDetached()
        fun fetch(id: Int, type: String)
        fun renderDetails(details: DetailDataModel, type: String)
        fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?)
    }

    interface Repository{
        fun setInteractor(interactor: Interactor)
        fun fetch(id: Int, type: String)
    }
}