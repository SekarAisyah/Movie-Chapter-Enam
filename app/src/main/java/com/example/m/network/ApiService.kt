package com.example.m.network

import com.example.m.model.DetailMovieItem
import com.example.m.model.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=315cd48d400d88e273e8050a32df1873&language=en-US&page=1")
    fun getMoviePopular(): Call<PopularMovieResponse>

    @GET("movie/{id}?api_key=315cd48d400d88e273e8050a32df1873")
    fun getMovieDetail(@Path("id") id: Int): Call<DetailMovieItem>

}