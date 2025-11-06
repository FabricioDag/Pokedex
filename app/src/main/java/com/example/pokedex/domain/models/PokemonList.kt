package com.example.pokedex.domain.models

data class PokemonList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

//TODO should it be here?
data class PokemonListState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val error: String? = null,
    val selectedType: Int? = null,
    val selectedGeneration: Int? = null,
)

// TODO remove
data class PokemonListStateNew(
    val pokemonList: List<Pokemon> = emptyList(),// pokemons
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "", // searchQuery
    val selectedType: String? = null,
    val selectedRegion: String? = null,
    val nextPageUrl: String? = null,
    val offset: Int = 0
)