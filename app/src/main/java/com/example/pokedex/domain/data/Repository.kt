package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.GenerationResponse
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.domain.models.TypeResponse
import retrofit2.Response

class Repository {
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): Response<PokemonList> {
        return RemoteApiProvider.service.getPokemon(offset, limit)
    }

    suspend fun getPokemonDetail(name: String): Response<PokemonDetail> {
        return RemoteApiProvider.service.getPokemonDetail(name)
    }

    suspend fun getGeneration(id: Int): Response<GenerationResponse> {
        return RemoteApiProvider.service.getGeneration(id)
    }

    suspend fun getType(id: Int): Response<TypeResponse> {
        return RemoteApiProvider.service.getType(id)
    }
}