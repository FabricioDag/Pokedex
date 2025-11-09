package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.GenerationResponse
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.domain.models.TypeResponse
import com.example.pokedex.gateway.RemoteApiProvider
import retrofit2.Response

class Repository {
    suspend fun getPokemonListOld(offset: Int = 0, limit: Int = 20): Response<PokemonList> {
        return RemoteApiProvider.service.getPokemon(offset, limit)
    }

    suspend fun getPokemonDetailOld(name: String): Response<PokemonDetail> {
        return RemoteApiProvider.service.getPokemonDetail(name)
    }

    suspend fun getGenerationOld(id: Int): Response<GenerationResponse> {
        return RemoteApiProvider.service.getGeneration(id)
    }

    suspend fun getTypeOld(id: Int): Response<TypeResponse> {
        return RemoteApiProvider.service.getType(id)
    }
}