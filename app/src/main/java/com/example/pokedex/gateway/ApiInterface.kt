package com.example.pokedex.gateway

import com.example.pokedex.domain.models.GenerationResponse
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.domain.models.TypeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Response<PokemonDetail>

    @GET("generation/{id}")
    suspend fun getGeneration(
        @Path("id") id: Int
    ): Response<GenerationResponse>

    @GET("type/{id}")
    suspend fun getType(
        @Path("id") id: Int
    ): Response<TypeResponse>
}