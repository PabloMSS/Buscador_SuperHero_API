package com.example.buscador_superhero_api

import com.example.buscador_superhero_api.Models.SuperHeroDataResponse
import com.example.buscador_superhero_api.Models.SuperHeroDetailResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/1763924037406724/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superHeroName: String): Response<SuperHeroDataResponse>

    @GET("/api/1763924037406724/{id}")
    suspend fun getSuperHeroeId(@Path("id") superHeroId: String): Response<SuperHeroDetailResponse>

}