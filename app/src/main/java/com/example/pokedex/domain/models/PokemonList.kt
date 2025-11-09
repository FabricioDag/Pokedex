package com.example.pokedex.domain.models

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

//TODO should it be here?
