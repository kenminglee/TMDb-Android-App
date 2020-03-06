package com.example.tmdb.UI.DetailFragment

import com.example.tmdb.Model.Network.ApiFactory
import com.example.tmdb.Model.Network.TmdbApi
import com.example.tmdb.UI.DataModel.DetailDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DetailModel : DetailContract.Model {
    private lateinit var presenter: DetailContract.Presenter
    private var service: TmdbApi = ApiFactory()
        .getRetrofitClient()

    override fun setPresenter(presenter: DetailContract.Presenter) {
        this.presenter = presenter
    }

    override fun fetch(id: Int, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDetails(type, id)
            withContext(Dispatchers.Main){
                try{
                    val temp = response.body()
                    if (response.isSuccessful) presenter.renderDetails(
                        DetailDataModel(
                            temp?.imdb_id ?: "",
                            temp?.overview ?: temp?.biography ?: "",
                            temp?.vote_count ?: 0,
                            temp?.backdrop_path ?: temp?.profilePath ?: temp?.poster_path ?: ""
                        ), type)
                } catch (e: Exception) {e.printStackTrace()}
            }
        }
    }
}