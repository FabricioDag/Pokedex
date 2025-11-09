package com.example.pokedex.features.dashboard

import com.example.pokedex.domain.models.Pokemon

data class DashboardUIState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val error: String? = null,
    val selectedType: Int? = null,
    val selectedGeneration: Int? = null,
)
