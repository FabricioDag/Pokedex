package com.example.pokedex.domain.models

data class GenerationResponse(
    val id: Int,
    val main_region: RegionInfo,
    val pokemon_species: List<PokemonSpecies>
)

data class RegionInfo(
    val name: String,
    val url: String
)

data class PokemonSpecies(
    val name: String,
    val url: String
)