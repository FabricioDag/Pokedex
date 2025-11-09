package com.example.pokedex.gateway

import com.example.pokedex.domain.interfaces.PokemonRepository
import com.example.pokedex.domain.models.GenerationResponse
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import com.example.pokedex.domain.models.TypeResponse
import retrofit2.Response

class PokedexApiRepository: PokemonRepository {
    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): Response<PokemonList> {

        return RemoteApiProvider.service.getPokemon(offset,limit)
    }

    override suspend fun getPokemonDetail(name: String): Response<PokemonDetail> {
        return RemoteApiProvider.service.getPokemonDetail(name)
    }

    override suspend fun getGeneration(id: Int): Response<GenerationResponse> {
        return RemoteApiProvider.service.getGeneration(id)
    }

    override suspend fun getType(id: Int): Response<TypeResponse> {
        return RemoteApiProvider.service.getType(id)
    }


}