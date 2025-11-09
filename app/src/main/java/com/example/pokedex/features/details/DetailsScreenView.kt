package com.example.pokedex.features.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.pokedex.domain.data.Repository
import com.example.pokedex.domain.models.PokemonDetail
import com.example.pokedex.domain.models.PokemonStat
import com.example.pokedex.domain.models.TypeSlot
import com.example.pokedex.domain.models.getTypeColor
import com.example.pokedex.navigation.DashboardScreen
import com.example.pokedex.navigation.SplashScreen
import com.example.pokedex.features.details.DetailsViewModel
import kotlinx.coroutines.delay

@Composable
fun DetailsScreenView(
    navController: NavHostController,
    name: String,
    viewModel: DetailsViewModel = viewModel()
) {
    val state = viewModel.uiState

    LaunchedEffect(name) {
        viewModel.loadPokemonDetail(name)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = state.error,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        state.pokemon != null -> {
            val pokemon = state.pokemon
            val primaryTypeColor = getTypeColor(pokemon.types.firstOrNull()?.type?.name ?: "normal")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                primaryTypeColor,
                                Color.White
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DetailsTopBar(
                    number = pokemon.id.toString(),
                    onBackClick = { navController.popBackStack()},
                    onFavoriteClick = {handleFavoriteClick()}
                )




                AsyncImage(
                    model = pokemon.sprites.frontDefault,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(300.dp)
                )

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )


                Text("Height: ${pokemon.height}")
                Text("Weight: ${pokemon.weight}")



                PokemonTypes(pokemon.types)

                StatsSection(pokemon.stats)
            }
        }}

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Pokémon não encontrado.")
            }
        }
    }
}

@Composable
fun DetailsTopBar(
    number: String,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        Text(
            text = "#${number}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favoritar",
                tint = Color.Red
            )
        }
    }
}

private  fun handleFavoriteClick(): Unit{
    print("Not implemented Yet")
}

@Composable
fun StatsSection(stats: List<PokemonStat>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        stats.forEach { stat ->
            val statName = when (stat.stat.name) {
                "hp" -> "HP"
                "attack" -> "ATK"
                "defense" -> "DEF"
                "special-attack" -> "SP. ATK"
                "special-defense" -> "SP. DEF"
                "speed" -> "SPD"
                else -> stat.stat.name.uppercase()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = statName,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.width(80.dp)
                )

                LinearProgressIndicator(
                    progress = (stat.baseStat / 200f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = when (stat.stat.name) {
                        "hp" -> Color(0xFFEF5350)
                        "attack" -> Color(0xFFF57C00)
                        "defense" -> Color(0xFF42A5F5)
                        "special-attack" -> Color(0xFFAB47BC)
                        "special-defense" -> Color(0xFF26A69A)
                        "speed" -> Color(0xFFFFEE58)
                        else -> MaterialTheme.colorScheme.primary
                    },
                    trackColor = Color.LightGray.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stat.baseStat.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PokemonTypes(types: List<TypeSlot>) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        types.forEach { slot ->
            val typeName = slot.type.name
            val backgroundColor = getTypeColor(typeName)

            Box(
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = typeName.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}