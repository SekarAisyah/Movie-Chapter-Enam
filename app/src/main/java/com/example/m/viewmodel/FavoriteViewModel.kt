package com.example.m.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.m.room.FavoriteMovie
import com.example.m.room.FavoriteMovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val db: FavoriteMovieDao) : ViewModel() {
    private val _movie: MutableLiveData<FavoriteMovie> = MutableLiveData()
    val movie: LiveData<FavoriteMovie> get() = _movie

    private val _ListMovie: MutableLiveData<List<FavoriteMovie>> = MutableLiveData()
    val listMovie: LiveData<List<FavoriteMovie>> get() = _ListMovie

    fun getAllFavoriteMovie() {
        GlobalScope.launch {
            _ListMovie.postValue(db.getAllFavorite())
        }
    }

    fun deleteFavMovie(favMovie: FavoriteMovie) {
        GlobalScope.launch {
            db.deleteFavorite(favMovie)
            _movie.postValue(favMovie)
        }
    }
}