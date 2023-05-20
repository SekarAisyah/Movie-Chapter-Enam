package com.example.m.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m.R
import com.example.m.databinding.ItemFavoriteBinding
import com.example.m.room.FavoriteMovie

class FavoriteMovieAdapter(private val listMovie: List<FavoriteMovie>) : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindFilms(movie: FavoriteMovie) {
            with(itemView) {
                binding.apply {
                    binding.favfilm = movie
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w400${movie.posterPath}")
                        .into(binding.imgFilm)
                    cardView.setOnClickListener{
                        val bundle = Bundle().apply {
                            putInt("ID", movie.id.toString().toInt())
                        }
                        it.findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilms(listMovie[position])
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

}