package com.example.m.view.ui

import android.annotation.SuppressLint
import com.example.m.R
import com.example.m.databinding.FragmentDetailBinding
import com.example.m.room.FavoriteMovie
import com.example.m.viewmodel.DetailViewModel
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@Suppress("KotlinConstantConditions")
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var selectedMovie: FavoriteMovie
    private var isFavorite by Delegates.notNull<Boolean>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getMovieById(id)
            observeDetailMovie()
            setFavoriteListener()
            checkFavoriteMovie(id)

            //Crashlytics
            binding.btnCrashdetail.setOnClickListener {
                throw RuntimeException("Test Crash") // Force a crash
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeDetailMovie() {
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    binding.tvNamafilmdetail.text = it.title.toString()
                    binding.tvReleasefilmdetail.text = "Release: " + it.releaseDate.toString()
                    binding.tvPopularitydetail.text = "Popularity: " + it.popularity.toString()
                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500/" + it.backdropPath)
                        .into(binding.ivFilmimagedetail)

                    binding.tvSinopsisfilmdetail.text = """Overview:
                        |
                    """.trimMargin() + it.overview.toString()
                    selectedMovie = FavoriteMovie(
                        it.id!!,
                        it.title!!,
                        it.releaseDate!!,
                        it.posterPath!!
                    )
                }
            }
        }
    }

    private fun setFavoriteListener() {
        binding.icFav.apply {
            setOnClickListener {
                isFavorite = if (!isFavorite) {
                    addToFavorite(selectedMovie)
                    binding.icFav.setImageResource(R.drawable.ic_baseline_favorite_red)
                    true
                } else {
                    deleteFromFavorite(selectedMovie)
                    binding.icFav.setImageResource(R.drawable.iv_baseline_delete)
                    false
                }
            }
        }
    }

    private fun addToFavorite(movie: FavoriteMovie) {
        viewModel.addFavMovie(movie)
        viewModel.favMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun deleteFromFavorite(movie: FavoriteMovie) {
        viewModel.deleteFavMovie(movie)
        viewModel.deleteFavMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavoriteMovie(movieId: Int) {
        viewModel.isFavoriteMovie(movieId)
        viewModel.isFav.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it) {
                    isFavorite = true
                    binding.icFav.setImageResource(R.drawable.ic_baseline_favorite)
                } else {
                    isFavorite = false
                    binding.icFav.setImageResource(R.drawable.iv_baseline_delete)
                }
            } else {
                Log.d("CHECK_FAV", "checkFavoriteMovie: $it")
            }
        }
    }

}