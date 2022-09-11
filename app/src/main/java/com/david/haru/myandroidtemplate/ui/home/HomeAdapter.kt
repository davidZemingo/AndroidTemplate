package com.david.haru.myandroidtemplate.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.david.haru.myandroidtemplate.R
import com.david.haru.myandroidtemplate.databinding.ListItemBinding
import com.david.haru.myandroidtemplate.network.MovieItem
import com.david.haru.myandroidtemplate.network.getImageUrl

class HomeAdapter(
    private val callback: (MovieItem, ListItemBinding) -> Unit
    ) :
    PagingDataAdapter<MovieItem, HomeAdapter.MovieViewHolder>(
        HomeAdapterDiffCallback()
    ) {

    private lateinit var mInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        if (!::mInflater.isInitialized) {
            mInflater = LayoutInflater.from(parent.context)
        }

        return MovieViewHolder(mInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)!!
        holder.onBind(movieItem)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ListItemBinding.bind(itemView)

        fun onBind(dataMovieItem: MovieItem) = with(binding) {
            itemView.tag = dataMovieItem
            title.text = dataMovieItem.title.trim()
            image.load(dataMovieItem.getImageUrl())

            itemView.setOnClickListener {
                val data = itemView.tag as MovieItem
                callback(data, binding)

            }
        }
    }

}

class HomeAdapterDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldMovieItem: MovieItem, newMovieItem: MovieItem): Boolean {
        return oldMovieItem.hashCode() == newMovieItem.hashCode()
    }

    override fun areContentsTheSame(oldMovieItem: MovieItem, newMovieItem: MovieItem): Boolean {
        return oldMovieItem == newMovieItem
    }
}


