package com.example.tmdb.ui.searchfragment

import com.example.tmdb.model.network.ApiFactory
import com.example.tmdb.model.network.TmdbApi
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SearchModel : SearchContract.Model {
    private lateinit var presenter: SearchContract.Presenter
    private var service: TmdbApi = ApiFactory().getRetrofitClient()

    override fun setPresenter(presenter: SearchContract.Presenter) {
        this.presenter = presenter
    }

    override fun queryMulti(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.query(ResultsAdapter.MULTI, keyword)
            withContext(Dispatchers.Main){
                try{
                    if (response.isSuccessful){
                        val temp = response.body()?.results?.map { tmdbData ->
                            SearchAndBrowseDataModel(
                                tmdbData.id?: 0,
                                tmdbData.mediaType ?: "",
                                tmdbData.name ?: tmdbData.title ?: "",
                                tmdbData.vote_average ?: -1.0,
                                tmdbData.poster_path ?: tmdbData.profilePath ?: tmdbData.backdrop_path ?: ""
                            )
                        }

                        presenter.findPositionAndRenderResults(temp?: listOf(), ResultsAdapter.MULTI)
                    }
                } catch (e: Exception) {e.printStackTrace()}
            }
        }
    }

    override fun querySpecific(keyword: String, type: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.query(type, keyword)
            withContext(Dispatchers.Main){
                try{
                    if (response.isSuccessful){
                        val results = response.body()?.results?.map { tmdbData ->
                            SearchAndBrowseDataModel(
                                tmdbData.id?: 0,
                                type,
                                tmdbData.name ?: tmdbData.title ?: "",
                                tmdbData.vote_average ?: -1.0,
                                tmdbData.poster_path ?: tmdbData.profilePath ?: tmdbData.backdrop_path ?: ""
                            )
                        }?: listOf()

                        presenter.findPositionAndRenderResults(results, type)
                    }
                } catch (e: Exception) {e.printStackTrace()}
            }
        }
    }
}

