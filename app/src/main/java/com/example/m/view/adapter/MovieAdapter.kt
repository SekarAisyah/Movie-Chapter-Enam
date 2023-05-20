package com.example.m.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m.R
import com.example.m.databinding.ItemFilmBinding
import com.example.m.model.PopularMovieItem

class MovieAdapter(private var listFilm: List<PopularMovieItem>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindFilms(itemFilms: PopularMovieItem) {
            binding.film = itemFilms
            Glide.with(itemView).load(IMAGE_BASE + itemFilms.posterPath).into(binding.imgFilm)
            binding.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putInt("ID", itemFilms.id.toString().toInt())
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFilms(listFilm[position])
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500${listFilm[position].posterPath}")
            .into(holder.binding.imgFilm)
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

}