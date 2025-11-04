package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {
    @GET("pokemon")
    suspend fun getPokemon(

    ): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): Response<PokemonDetail>
}

object RemoteApiProvider {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    val service: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiInterface::class.java)
    }
}