package com.example.pokedex.domain.models

data class TypeResponse(
    val id: Int,
    val name: String,
    val pokemon: List<TypePokemonEntry>
)

data class TypePokemonEntry(
    val pokemon: TypePokemon
)

data class TypePokemon(
    val name: String,
    val url: String
)
//TODO: Use the same pokemon data class