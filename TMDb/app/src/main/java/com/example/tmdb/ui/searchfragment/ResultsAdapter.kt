package com.example.tmdb.UI.SearchFragment

import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.UI.DataModel.SearchAndBrowseDataModel
import kotlinx.android.synthetic.main.fragment_search_items.view.*

class ResultsAdapter(var presenter: SearchContract.Presenter) : RecyclerView.Adapter<ResultsAdapter.ResultsHolder>() {
    private var results:List<SearchAndBrowseDataModel> = listOf()

    inner class ResultsHolder(private val view: View): RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                presenter.onClickResult(results[adapterPosition], adapterPosition)
            }
        }

        fun show(type: String, name: String, pictureUrl: String, rating: Double) {
            lateinit var str: SpannableString
            when(type){
                TV -> {
                    str = SpannableString(view.context.getString(R.string.TVShow))
                    str.setSpan(BackgroundColorSpan(Color.GREEN), 0, str.length, 0)
                }
                MOVIE -> {
                    str = SpannableString(view.context.getString(R.string.Movie))
                    str.setSpan(BackgroundColorSpan(Color.RED), 0, str.length, 0)
                }
                PERSON -> {
                    str = SpannableString(view.context.getString(R.string.People))
                    str.setSpan(BackgroundColorSpan(Color.BLUE), 0, str.length, 0)
                    view.ratingbar.visibility = View.GONE
                }
            }
            view.ratingbar.rating = (rating/2).toFloat()
            view.subject.text = str
            view.title.text = name
            view.my_image_view2.setImageURI(view.context.getString(R.string.imageUrl, pictureUrl))
        }
    }

    fun setResults(results: List<SearchAndBrowseDataModel>){
        this.results = results
    }


    override fun getItemCount(): Int = results.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_items, parent,false)
        return ResultsHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
        val item = results[position]
        holder.show(item.type, item.name, item.pictureUrl, item.rating)
    }

    companion object {
        const val TV = "tv"
        const val MOVIE = "movie"
        const val PERSON = "person"
        const val MULTI = "multi"
    }

}
