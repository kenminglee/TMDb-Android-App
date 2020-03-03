package com.example.tmdb.ui.detailfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tmdb.R

class DetailRouter: DetailContract.Router {
    override fun navigateToWebView(fragment: Fragment, fragmentManager: FragmentManager?) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.container, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }
}