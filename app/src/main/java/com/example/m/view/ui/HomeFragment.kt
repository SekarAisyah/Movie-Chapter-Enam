package com.example.m.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m.R
import com.example.m.databinding.FragmentHomeBinding
import com.example.m.view.adapter.MovieAdapter
import com.example.m.viewmodel.MovieViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        observeMovie()
        (activity as AppCompatActivity).setSupportActionBar(binding.tbHome)

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(R.id.action_loginFragment2_to_homeFragment)
        }

        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        val getUser = sharedPreferences.getString("user", "")
        binding.textJudul.text = "Welcome, $getUser"

        binding.ivIcprofile.setOnClickListener {
            val addUser = sharedPreferences.edit()
            addUser.putString("user", getUser)
            addUser.apply()
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.ivIcfavorit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
//        val viewModelMovie = ViewModelProvider(this)[ViewModelFilmPopular::class.java]
//        viewModelMovie.callTmdb()
//        viewModelMovie.liveDataMovie.observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.rvListfilm.layoutManager =
//                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                binding.rvListfilm.adapter = AdapterFilm(it)
//            }
//        }

    }
    private fun observeMovie(){
        binding.rvListFilm.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListFilm.setHasFixedSize(false)
        viewModel.setMoviesList()
        viewModel.movie.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvListFilm.adapter = MovieAdapter(it)
            }
        }
    }

}