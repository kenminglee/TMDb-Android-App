package com.example.tmdb.ui.detailfragment

import com.example.tmdb.model.network.*
import com.example.tmdb.ui.datamodel.DetailDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRepository : DetailContract.Repository {
    private lateinit var interactor: DetailContract.Interactor
    private var service: TmdbApi = ApiFactory().getRetrofitClient()

    override fun setInteractor(interactor: DetailContract.Interactor) {
        this.interactor = interactor
    }

    override fun fetch(id: Int, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDetails(type, id)
            withContext(Dispatchers.Main){
                try{
                    val temp = response.body()
                    if (response.isSuccessful) interactor.renderDetails(
                        DetailDataModel(
                        temp?.imdb_id?: "",
                        temp?.overview?: temp?.biography?: "",
                        temp?.vote_count?:0,
                        temp?.backdrop_path?: temp?.profilePath?: temp?.poster_path?: ""), type)
                } catch (e: Exception) {e.printStackTrace()}
            }
        }
    }
}