package com.example.m.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM FavoriteMovie")
    fun getAllFavorite() : List<FavoriteMovie>

    @Query("SELECT EXISTS(SELECT id FROM FavoriteMovie WHERE id = :id)")
    fun isFavoriteMovie(id : Int) : Boolean

    @Insert
    fun addFavorite(favoriteMovie: FavoriteMovie)

    @Delete
    fun deleteFavorite(favoriteMovie: FavoriteMovie)
}