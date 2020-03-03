package com.example.tmdb.UI.BrowseFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.ui.datamodel.SearchAndBrowseDataModel
import kotlinx.android.synthetic.main.fragment_browse_items.view.*

class MoviesAdapter(var movies: List<SearchAndBrowseDataModel>, val viewModel: BrowseContract.ViewModel) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    inner class MovieHolder(val view: View): RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener {
                viewModel.onMovieClick(movies[adapterPosition])
            }
        }

        fun displayMovieInfo(text: String, uri: String, rating: Double){
            view.movie_title.text = text
            view.my_image_view.setImageURI("https://image.tmdb.org/t/p/original$uri")
            view.ratingbar.rating = (rating/2).toFloat()
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_browse_items, parent,false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movies[position]
        holder.displayMovieInfo(movie.name, movie.pictureUrl, movie.rating)
    }

    companion object{
        const val TOP_RATED = "top_rated"
        const val UPCOMING = "upcoming"
        const val NOW_PLAYING = "now_playing"
    }
}


