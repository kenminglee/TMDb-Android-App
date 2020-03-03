package com.example.tmdb.ui.detailfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdb.R
import com.example.tmdb.ui.datamodel.DetailDataModel
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel

class DetailViewModel(application: Application): AndroidViewModel(application), DetailContract.ViewModel{
    private var presenter: DetailContract.Presenter? = null

    private val _body = MutableLiveData("")
    private val _rating = MutableLiveData<Float>(0.0f)
    private val _name = MutableLiveData<String>("")
    private val _subject = MutableLiveData<String>("")

    val body: LiveData<String> = _body
    val rating: LiveData<Float> = _rating
    val name: LiveData<String> = _name
    val subject: LiveData<String> = _subject

    private lateinit var movie: SearchAndBrowseDataModel
    fun setMovie(movie: SearchAndBrowseDataModel){
        this.movie = movie
    }

    override fun renderDetails(details: DetailDataModel, type: String) {
        _name.value = movie.name
        _rating.value = (movie.rating/2).toFloat()
        _body.value = if(details.detail.isEmpty()) getApplication<Application>().getString(R.string.NoDetailsProvided) else details.detail
    }

    override fun getPresenter(): DetailContract.Presenter {
        if(presenter == null){
            presenter = DetailPresenter()
        }
        return presenter as DetailContract.Presenter
    }
}