package com.example.pokedex.domain.models

import com.squareup.moshi.Json

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<TypeSlot>
)

data class Sprites(
    @Json(name = "front_default") val frontDefault: String
)

data class TypeSlot(
    val type: TypeInfo
)

data class TypeInfo(
    val name: String
)