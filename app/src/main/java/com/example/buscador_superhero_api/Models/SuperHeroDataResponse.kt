package com.example.buscador_superhero_api.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperheroItemResponse>
)

data class SuperheroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: SuperheroImageResponse
)

data class SuperheroImageResponse(
    @SerializedName("url") val url: String
)