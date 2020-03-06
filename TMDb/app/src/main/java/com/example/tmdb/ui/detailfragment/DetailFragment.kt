package com.example.tmdb.UI.DetailFragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.UI.DataModel.DetailDataModel
import com.example.tmdb.UI.DataModel.SearchAndBrowseDataModel
import com.example.tmdb.UI.SearchFragment.ResultsAdapter
import com.example.tmdb.UI.WebViewFragment.WebViewFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlin.math.abs

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    DetailContract.View {
    private lateinit var detail: SearchAndBrowseDataModel
    private lateinit var presenter: DetailContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter =
            DetailPresenter(DetailModel())
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
    ): View? = inflater.inflate(R.layout.fragment_detail, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        app_bar_layout.addOnOffsetChangedListener(this)

        detail_BackArrow.setOnClickListener{
//            activity?.onBackPressed()
            activity?.supportFragmentManager?.popBackStack()

        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (abs(verticalOffset) >= appBarLayout?.totalScrollRange ?:0)
            // Collapsed
            detail_collapsing_toolbar.title = detail_name.text
        else detail_collapsing_toolbar.title = ""

    }


    override fun renderDetails(details: DetailDataModel, type: String) {
        var str = SpannableString("")
        when(type){
            ResultsAdapter.MOVIE -> {
                str = SpannableString(getString(R.string.Movie))
                str.setSpan(BackgroundColorSpan(Color.RED), 0, str.length, 0)
            }
            ResultsAdapter.PERSON -> {
                str = SpannableString(getString(R.string.People))
                str.setSpan(BackgroundColorSpan(Color.BLUE), 0, str.length, 0)
                detail_ratingbar.visibility = View.GONE
            }
            ResultsAdapter.TV -> {
                str = SpannableString(getString(R.string.TVShow))
                str.setSpan(BackgroundColorSpan(Color.GREEN), 0, str.length, 0)
            }
        }
        expandedImage.setImageURI(getString(R.string.imageUrl, details.imageUrl))
        detail_subject.text = str
        detail_name.text = detail.name
        detail_ratingbar.rating = (detail.rating/2).toFloat()
        if(details.detail.isEmpty()) detail_body.text = getString(R.string.NoDetailsProvided) else detail_body.text = details.detail
        if(details.imdbId.isNotEmpty()){
            val url = if(type == ResultsAdapter.PERSON) getString(R.string.IMDbName)+details.imdbId else getString(R.string.IMDbTitle)+details.imdbId
            detail_button.setOnClickListener{
                navigateToNewFragment(WebViewFragment.newInstance(url, detail.name))
            }
            detail_button.visibility = View.VISIBLE
        }
    }

    override fun getDetailsId(): Int {
        return detail.id
    }

    override fun getType(): String {
        return detail.type
    }

    private fun navigateToNewFragment(fragment: Fragment){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment, null)
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object{
        fun newInstance(detail: SearchAndBrowseDataModel): DetailFragment = DetailFragment().apply {
            this.detail = detail
        }
    }

}