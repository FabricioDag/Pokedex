package com.example.pokedex.domain.models

import androidx.compose.ui.graphics.Color
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

fun getTypeColor(typeName: String): Color {
    return when (typeName.lowercase()) {
        "fire" -> Color(0xFFFFA756)
        "water" -> Color(0xFF58ABF6)
        "grass" -> Color(0xFF8BBE8A)
        "electric" -> Color(0xFFF2CB55)
        "bug" -> Color(0xFF8BD674)
        "poison" -> Color(0xFF9F6E97)
        "psychic" -> Color(0xFFFF6568)
        "ground" -> Color(0xFFD97845)
        "fairy" -> Color(0xFFEBA8C3)
        "fighting" -> Color(0xFFD04164)
        "rock" -> Color(0xFFBAAB82)
        "ghost" -> Color(0xFF8571BE)
        "ice" -> Color(0xFF91D8DF)
        else -> Color(0xFFA8A77A)
    }
}