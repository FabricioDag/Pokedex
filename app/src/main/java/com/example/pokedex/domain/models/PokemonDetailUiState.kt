package com.example.pokedex.domain.models

data class PokemonDetailUiState(
    val pokemon: PokemonDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
//TODO maybe UiStates like this?
