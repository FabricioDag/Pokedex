package com.example.pokedex.domain.data

import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonList
import retrofit2.Response

class Repository {
    suspend fun getPokemonList(): Response<PokemonList>{
        return RemoteApiProvider.service.getPokemon()
    }

    suspend fun getPokemonDetail(name: String): Response<PokemonDetail> {
        return RemoteApiProvider.service.getPokemonDetail(name)
    }
}