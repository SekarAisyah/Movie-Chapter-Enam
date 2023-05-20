@file:Suppress("unused")

package com.example.m.view.ui

import android.os.Bundle
import com.example.m.databinding.FragmentFavoriteBinding
import com.example.m.room.MovieDatabase
import com.example.m.view.adapter.FavoriteMovieAdapter
import com.example.m.viewmodel.FavoriteViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private var mDbFav: MovieDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this)[ViewModelFavoriteMovie::class.java]
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        getAllFavMovies()
    }


//    private fun getAllFavMovies(){
//        viewModel.getAllFavoriteMovie()
//        viewModel.listMovie.observe(viewLifecycleOwner){
//            if(it != null){
//                binding.rvListfilmfav.layoutManager = LinearLayoutManager(requireContext())
//                binding.rvListfilmfav.setHasFixedSize(false)
//                binding.rvListfilmfav.adapter = AdapterFavorite(it)
//            }
//        }
//    }

    private fun getAllFavMovies(){
        viewModel.getAllFavoriteMovie()
        viewModel.listMovie.observe(viewLifecycleOwner){
            if(it != null){
                binding.rvListFilm.layoutManager = LinearLayoutManager(requireContext())
                binding.rvListFilm.setHasFixedSize(false)
                binding.rvListFilm.adapter = FavoriteMovieAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}