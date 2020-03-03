package com.example.tmdb.ui.detailfragment

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentDetailBinding
import com.example.tmdb.ui.datamodel.DetailDataModel
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import com.example.tmdb.ui.searchfragment.ResultsAdapter
import com.example.tmdb.ui.webviewfragment.WebViewFragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlin.math.abs

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, DetailContract.View{
    private lateinit var detail: SearchAndBrowseDataModel
    private lateinit var presenter: DetailContract.Presenter
    private var binding: FragmentDetailBinding? = null
    private val vm by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = vm.getPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        presenter.onViewAttached(this, DetailRouter())
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
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val local_binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false)
        binding = local_binding
        local_binding.lifecycleOwner = this
        local_binding.viewmodel = vm
        return local_binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        app_bar_layout.addOnOffsetChangedListener(this)
        vm.setMovie(detail)
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
        if(details.imdbId.isNotEmpty()){
            val url = if(type == ResultsAdapter.PERSON) getString(R.string.IMDbName)+details.imdbId else getString(R.string.IMDbTitle)+details.imdbId
            detail_button.setOnClickListener{
                presenter.navigateToWebView(WebViewFragment.newInstance(url, detail.name), activity?.supportFragmentManager)
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

    override fun getViewModel(): DetailContract.ViewModel {
        return vm
    }

    companion object{
        fun newInstance(detail: SearchAndBrowseDataModel): DetailFragment = DetailFragment().apply {
            this.detail = detail
        }
    }

}