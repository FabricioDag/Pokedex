package com.example.pokedex.domain.interfaces

import com.example.pokedex.domain.models.GenerationResponse
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.domain.models.TypeResponse
import retrofit2.Response
// how could we get rid of retrofit from domain layer?

interface PokemonRepository {
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): Response<PokemonList>

    suspend fun getPokemonDetail(name: String): Response<PokemonDetail>

    suspend fun getGeneration(id: Int): Response<GenerationResponse>

    suspend fun getType(id: Int): Response<TypeResponse>
}